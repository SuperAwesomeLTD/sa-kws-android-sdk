package kws.superawesome.tv.kwssdk.process;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import kws.superawesome.tv.kwssdk.services.firebase.FirebaseGetToken;
import kws.superawesome.tv.kwssdk.services.firebase.FirebaseGetTokenInterface;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckAllowed;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckAllowedInterface;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckRegistered;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckRegisteredInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSPermissionStatus;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSRegisterToken;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSRegisterTokenInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSUnregisterToken;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSUnregisterTokenInterface;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public class KWSNotificationProcess {

    // private services
    private KWSCheckAllowed checkAllowed = null;
    private KWSCheckRegistered checkRegistered = null;
    private KWSRequestPermission requestPermission = null;
    private FirebaseGetToken getToken = null;
    private KWSRegisterToken registerToken = null;
    private KWSUnregisterToken unregisterToken = null;

    /**
     * Public class constructor
     */
    public KWSNotificationProcess() {
        checkAllowed = new KWSCheckAllowed();
        requestPermission = new KWSRequestPermission();
        checkRegistered = new KWSCheckRegistered();
        getToken = new FirebaseGetToken();
        registerToken = new KWSRegisterToken();
        unregisterToken = new KWSUnregisterToken();
    }

    /**
     * method that handles all the registration needs for the SDK
     * @param lis object to handle callbacks
     */
    public void register (final Context context, final KWSRegisterInterface lis) {
        // make sure it's not null
        KWSRegisterInterface local = new KWSRegisterInterface() { public void register(KWSNotificationStatus type) {}};
        final KWSRegisterInterface listener = lis != null ? lis : local;

        // 1 - check if the user is allowed Remote Notifications in KWS
        checkAllowed.execute(context, new KWSCheckAllowedInterface() {
            @Override
            public void allowed(boolean allowed) {
                // 1.1 - user is not allowed by parent in KWS to have Remote Notifications
                if(!allowed) {
                    listener.register(KWSNotificationStatus.ParentDisabledNotifications);
                    return;
                }

                // 1.2 - if user is allowed, request a new permission
                requestPermission.execute(context, new KWSPermissionType[]{KWSPermissionType.sendPushNotification}, new KWSRequestPermissionInterface() {
                    @Override
                    public void requested(KWSPermissionStatus status) {

                        switch (status) {

                            case Success: {

                                // 2.1 - check if user has Google Play Services
                                if (!checkPlayServices(context)) {
                                    listener.register(KWSNotificationStatus.FirebaseNotSetup);
                                    return;
                                }

                                // 2.2 - if all is OK so far, ask Firebase for a token
                                getToken.execute(new FirebaseGetTokenInterface() {
                                    @Override
                                    public void gotToken(boolean success, final String token) {

                                        // 3.1 - in this case, Firebase could not get a token
                                        if (!success) {
                                            listener.register(KWSNotificationStatus.FirebaseCouldNotGetToken);
                                            return;
                                        }

                                        // 3.2 - if finally I can get a token, register it with KWS
                                        registerToken.execute(context, token, new KWSRegisterTokenInterface() {
                                            @Override
                                            public void registered(boolean success) {

                                                // 4.1 - could not register it b/c network issues
                                                if (!success) {
                                                    listener.register(KWSNotificationStatus.NetworkError);
                                                }
                                                // 4.2 - all is OK, user is finally registered
                                                else {
                                                    listener.register(KWSNotificationStatus.Success);
                                                }
                                            }
                                        });
                                    }
                                });

                                break;
                            }
                            case NoParentEmail: {
                                listener.register(KWSNotificationStatus.NoParentEmail);
                                break;
                            }
                            case NeworkError: {
                                listener.register(KWSNotificationStatus.NetworkError);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * Method that handles all un-registration needs
     * @param lis object to send callbacks
     */
    public void unregister (Context context, final KWSUnregisterInterface lis) {
        // check for errors
        KWSUnregisterInterface local = new KWSUnregisterInterface() {public void unregister(boolean unregistered) {}};
        final KWSUnregisterInterface listener = lis != null ? lis : local;

        // get current token
        String token = getToken.getSavedToken();

        // unregister current token with KWS
        unregisterToken.execute(context, token, new KWSUnregisterTokenInterface() {
            @Override
            public void unregistered(boolean success) {
               listener.unregister(success);
            }
        });
    }

    /**
     * Method that handles checking if a user is registered or not
     * @param lis object to send callbacks
     */
    public void isRegistered (final Context context, final KWSIsRegisteredInterface lis) {
        // check for error
        KWSIsRegisteredInterface local = new KWSIsRegisteredInterface() {public void isRegistered(boolean registered) {}};
        final KWSIsRegisteredInterface listener = lis != null ? lis : local;

        // 1. check if user is still allowed to have Remote Notifications
        checkAllowed.execute(context, new KWSCheckAllowedInterface() {
            @Override
            public void allowed(boolean allowed) {

                // 1.2 - user is actually not allowed to have remote notifications
                if (!allowed) {
                    listener.isRegistered(false);
                    return;
                }

                // 1.3 - if all is ok, check if the user is already registered
                checkRegistered.execute(context, new KWSCheckRegisteredInterface() {
                    @Override
                    public void allowed(boolean registered) {
                        listener.isRegistered(registered);
                    }
                });
            }
        });
    }

    /**
     * Private function that determines if Google Play Services are installed
     * @return true or false
     */
    private boolean checkPlayServices(Context context) {

        boolean first = SAUtils.isClassAvailable("com.google.android.gms.common.ConnectionResult");
        boolean second = SAUtils.isClassAvailable("com.google.android.gms.common.GoogleApiAvailability");
        boolean third = SAUtils.isClassAvailable("com.google.firebase.iid.FirebaseInstanceId");
        boolean fourth = false;

        try {
            Class<?> googleapi = Class.forName("com.google.android.gms.common.GoogleApiAvailability");
            java.lang.reflect.Method method = googleapi.getMethod("getInstance");
            Object instance = method.invoke(googleapi);
            java.lang.reflect.Method method1 = googleapi.getMethod("isGooglePlayServicesAvailable", Context.class);
            Object returnValue = method1.invoke(instance, context);
            fourth = (int)returnValue == 0; // ConnectionResult.SUCCESS

        } catch (ClassNotFoundException e) {
            fourth = false;
        } catch (NoSuchMethodException e) {
            fourth = false;
        } catch (InvocationTargetException e) {
            fourth = false;
        } catch (IllegalAccessException e) {
            fourth = false;
        } catch (Exception e) {
            fourth = false;
        }

        return first && second && third && fourth;
    }
}

package kws.superawesome.tv.kwssdk.process;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import kws.superawesome.tv.kwssdk.services.firebase.FirebaseGetToken;
import kws.superawesome.tv.kwssdk.services.firebase.FirebaseGetTokenInterface;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckAllowed;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckAllowedInterface;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckRegistered;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSCheckRegisteredInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionStatus;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSRegisterToken;
import kws.superawesome.tv.kwssdk.services.kws.notifications.KWSRegisterTokenInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionInterface;
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
    public void register (final Context context, final KWSChildrenRegisterForRemoteNotificationsInterface lis) {
        // make sure it's not null
        KWSChildrenRegisterForRemoteNotificationsInterface local = new KWSChildrenRegisterForRemoteNotificationsInterface() { public void didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus type) {}};
        final KWSChildrenRegisterForRemoteNotificationsInterface listener = lis != null ? lis : local;

        // 1 - check if the user is allowed Remote Notifications in KWSChildren
        checkAllowed.execute(context, new KWSCheckAllowedInterface() {
            @Override
            public void allowed(boolean allowed) {
                // 1.1 - user is not allowed by parent in KWSChildren to have Remote Notifications
                if(!allowed) {
                    listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.ParentDisabledNotifications);
                    return;
                }

                // 1.2 - if user is allowed, request a new permission
                requestPermission.execute(context, new KWSChildrenPermissionType[]{KWSChildrenPermissionType.SendPushNotification}, new KWSChildrenRequestPermissionInterface() {
                    @Override
                    public void didRequestPermission(KWSChildrenRequestPermissionStatus status) {

                        switch (status) {

                            case Success: {

                                // 2.1 - check if user has Google Play Services
                                if (!checkPlayServices(context)) {
                                    listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.FirebaseNotSetup);
                                    return;
                                }

                                // 2.2 - if all is OK so far, ask Firebase for a token
                                getToken.execute(new FirebaseGetTokenInterface() {
                                    @Override
                                    public void gotToken(boolean success, final String token) {

                                        // 3.1 - in this case, Firebase could not getService a token
                                        if (!success) {
                                            listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.FirebaseCouldNotGetToken);
                                            return;
                                        }

                                        // 3.2 - if finally I can getService a token, registerForRemoteNotifications it with KWSChildren
                                        registerToken.execute(context, token, new KWSRegisterTokenInterface() {
                                            @Override
                                            public void registered(boolean success) {

                                                // 4.1 - could not registerForRemoteNotifications it b/c network issues
                                                if (!success) {
                                                    listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.NetworkError);
                                                }
                                                // 4.2 - all is OK, user is finally registered
                                                else {
                                                    listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.Success);
                                                }
                                            }
                                        });
                                    }
                                });

                                break;
                            }
                            case NoParentEmail: {
                                listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.NoParentEmail);
                                break;
                            }
                            case NetworkError: {
                                listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.NetworkError);
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
    public void unregister (Context context, final KWSChildrenUnregisterForRemoteNotificationsInterface lis) {
        // check for errors
        KWSChildrenUnregisterForRemoteNotificationsInterface local = new KWSChildrenUnregisterForRemoteNotificationsInterface() {public void didUnregisterForRemoteNotifications(boolean unregistered) {}};
        final KWSChildrenUnregisterForRemoteNotificationsInterface listener = lis != null ? lis : local;

        // getService current token
        String token = getToken.getSavedToken();

        // unregisterForRemoteNotifications current token with KWSChildren
        unregisterToken.execute(context, token, new KWSUnregisterTokenInterface() {
            @Override
            public void unregistered(boolean success) {
               listener.didUnregisterForRemoteNotifications(success);
            }
        });
    }

    /**
     * Method that handles checking if a user is registered or not
     * @param lis object to send callbacks
     */
    public void isRegistered (final Context context, final KWSChildrenIsRegisteredForRemoteNotificationsInterface lis) {
        // check for error
        KWSChildrenIsRegisteredForRemoteNotificationsInterface local = new KWSChildrenIsRegisteredForRemoteNotificationsInterface() {public void isRegisteredForRemoteNotifications(boolean registered) {}};
        final KWSChildrenIsRegisteredForRemoteNotificationsInterface listener = lis != null ? lis : local;

        // 1. check if user is still allowed to have Remote Notifications
        checkAllowed.execute(context, new KWSCheckAllowedInterface() {
            @Override
            public void allowed(boolean allowed) {

                // 1.2 - user is actually not allowed to have remote notifications
                if (!allowed) {
                    listener.isRegisteredForRemoteNotifications(false);
                    return;
                }

                // 1.3 - if all is ok, check if the user is already registered
                checkRegistered.execute(context, new KWSCheckRegisteredInterface() {
                    @Override
                    public void allowed(boolean registered) {
                        listener.isRegisteredForRemoteNotifications(registered);
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

package kws.superawesome.tv.kwssdk.process;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import kws.superawesome.tv.kwssdk.services.firebase.FirebaseGetToken;
import kws.superawesome.tv.kwssdk.services.firebase.FirebaseGetTokenInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSCheckAllowed;
import kws.superawesome.tv.kwssdk.services.kws.KWSCheckAllowedInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSCheckRegistered;
import kws.superawesome.tv.kwssdk.services.kws.KWSCheckRegisteredInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSRegisterToken;
import kws.superawesome.tv.kwssdk.services.kws.KWSRegisterTokenInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSUnregisterToken;
import kws.superawesome.tv.kwssdk.services.kws.KWSUnregisterTokenInterface;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public class NotificationProcess {

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
    public NotificationProcess () {
        checkAllowed = new KWSCheckAllowed();
        requestPermission = new KWSRequestPermission();
        checkRegistered = new KWSCheckRegistered();
        getToken = new FirebaseGetToken();
        registerToken = new KWSRegisterToken();
        unregisterToken = new KWSUnregisterToken();
    }

    /**
     * method that handles all the registration needs for the SDK
     * @param listener object to handle callbacks
     */
    public void register (@NonNull final RegisterInterface listener) {

        // 1 - check if the user is allowed Remote Notifications in KWS
        checkAllowed.execute(new KWSCheckAllowedInterface() {
            @Override
            public void checked(boolean success, boolean allowed) {
                // 1.1 - a network error occurred
                if (!success) {
                    listener.register(false, KWSErrorType.FailedToCheckIfUserHasNotificationsEnabledInKWS);
                    return;
                }

                // 1.2 - user is not allowed by parent in KWS to have Remote Notifications
                if(!allowed) {
                    listener.register(false, KWSErrorType.ParentHasDisabledRemoteNotifications);
                    return;
                }

                // 1.3 - if user is allowed, request a new permission
                requestPermission.execute(new KWSPermissionType[]{KWSPermissionType.sendPushNotification}, new KWSRequestPermissionInterface() {
                    @Override
                    public void requested(boolean success, boolean requested) {

                        // 2.1 - network error trying to request permission
                        if (!success) {
                            listener.register(false, KWSErrorType.FailedToRequestNotificationsPermissionInKWS);
                            return;
                        }

                        // 2.2 - requested ok, but user has no parent email
                        if (!requested) {
                            listener.register(false, KWSErrorType.UserHasNoParentEmail);
                            return;
                        }

                        // 2.3 - check if user has Google Play Services
                        if (!checkPlayServices()) {
                            listener.register(false, KWSErrorType.FirebaseNotSetup);
                            return;
                        }

                        // 2.4 - if all is OK so far, ask Firebase for a token
                        getToken.execute(new FirebaseGetTokenInterface() {
                            @Override
                            public void gotToken(boolean success, final String token) {

                                // 3.1 - in this case, Firebase could not get a token
                                if (!success) {
                                    listener.register(false, KWSErrorType.FirebaseCouldNotGetToken);
                                    return;
                                }

                                // 3.2 - if finally I can get a token, register it with KWS
                                registerToken.execute(token, new KWSRegisterTokenInterface() {
                                    @Override
                                    public void registered(boolean success) {

                                        // 4.1 - could not register it b/c network issues
                                        if (!success) {
                                            listener.register(false, KWSErrorType.FailedToSubscribeTokenToKWS);
                                        }
                                        // 4.2 - all is OK, user is finally registered
                                        else {
                                            Log.d("SuperAwesome", "Got Token! " + token);
                                            listener.register(true, null);
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Method that handles all un-registration needs
     * @param listener object to send callbacks
     */
    public void unregister (@NonNull final UnregisterInterface listener) {
        // get current token
        String token = getToken.getSavedToken();

        // unregister current token with KWS
        unregisterToken.execute(token, new KWSUnregisterTokenInterface() {
            @Override
            public void unregistered(boolean success) {
               listener.unregister(success);
            }
        });
    }

    /**
     * Method that handles checking if a user is registered or not
     * @param listener object to send callbacks
     */
    public void isRegistered (@NonNull final IsRegisteredInterface listener) {

        // 1. check if user is still allowed to have Remote Notifications
        checkAllowed.execute(new KWSCheckAllowedInterface() {
            @Override
            public void checked(boolean success, boolean allowed) {

                // 1.1 - network error trying to figure out if the user is allowed
                if (!success) {
                    listener.isRegistered(false);
                    return;
                }

                // 1.2 - user is actually not allowed to have remote notifications
                if (!allowed) {
                    listener.isRegistered(false);
                    return;
                }

                // 1.3 - if all is ok, check if the user is already registered
                checkRegistered.execute(new KWSCheckRegisteredInterface() {
                    @Override
                    public void checked(boolean success, boolean registered) {
                        // 2.1 - there was an error trying to figure out if the user is registered
                        if (!success) {
                            listener.isRegistered(false);
                        }
                        // 2.2 - just send whatever the server says it's ok
                        else {
                            listener.isRegistered(registered);
                        }
                    }
                });
            }
        });
    }

    /**
     * Private function that determines if Google Play Services are installed
     * @return true or false
     */
    private boolean checkPlayServices() {
        Context context = SAApplication.getSAApplicationContext();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }
}

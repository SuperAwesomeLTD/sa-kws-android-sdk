package kws.superawesome.tv.kwssdk;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSRegisterInterface {
    void kwsSDKDidRegisterUserForRemoteNotifications ();
    void kwsSDKDidFailToRegisterUserForRemoteNotificationsWithError (KWSErrorType type);
}

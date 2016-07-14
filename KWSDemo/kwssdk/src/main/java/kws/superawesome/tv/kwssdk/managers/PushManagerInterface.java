package kws.superawesome.tv.kwssdk.managers;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public interface PushManagerInterface {
    void didRegister (String token);
    void didUnregister ();
    void didFailBecauseNoGoogleServicesFound ();
    void didFailToGetFirebaseToken ();
    void networkErrorTryingToSubscribeToken ();
    void networkErrorTryingToUnsubscribeToken ();
}

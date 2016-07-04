package kws.superawesome.tv.kwssdk.firebase;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public interface FirebaseGetTokenInterface {
    void didGetFirebaseToken(String token);
    void didFailToGetFirebaseToken ();
    void didFailBecauseFirebaseIsNotSetup ();
}

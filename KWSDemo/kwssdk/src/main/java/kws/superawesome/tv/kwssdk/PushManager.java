package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import kws.superawesome.tv.kwslib.SAApplication;
import kws.superawesome.tv.kwssdk.kws.KWSSubscribeToken;
import kws.superawesome.tv.kwssdk.kws.KWSSubscribeTokenInterface;
import kws.superawesome.tv.kwssdk.kws.KWSUnsubscribeToken;
import kws.superawesome.tv.kwssdk.kws.KWSUnsubscribeTokenInterface;
import kws.superawesome.tv.kwssdk.firebase.FirebaseGetToken;
import kws.superawesome.tv.kwssdk.firebase.FirebaseGetTokenInterface;
import kws.superawesome.tv.kwssdk.push.PushRegisterPermission;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class PushManager {

    // singleton instance
    public static PushManager sharedInstance = new PushManager();

    // private variables
    private PushRegisterPermission pushRegister = null;
    private FirebaseGetToken firebaseGetToken = null;
    private KWSSubscribeToken subscribeToken = null;
    private KWSUnsubscribeToken unsubscribeToken = null;

    // listener
    public PushManagerInterface listener = null;

    // private constructor
    private PushManager () {
        firebaseGetToken = new FirebaseGetToken();
        pushRegister = new PushRegisterPermission();
        subscribeToken = new KWSSubscribeToken();
        unsubscribeToken = new KWSUnsubscribeToken();
    }

    // public function

    public void registerForPushNotifications () {

        if (!checkPlayServices()) {
            lisDidNotRegister();
        }
        else {

            firebaseGetToken.listener = new FirebaseGetTokenInterface() {
                @Override
                public void didGetFirebaseToken(final String token) {
                    Log.d("SuperAwesome", "Token is " + token);

                    pushRegister.register(token);
                    subscribeToken.listener = new KWSSubscribeTokenInterface() {
                        @Override
                        public void tokenWasSubscribed() {
                            lisDidRegister(token);
                        }

                        @Override
                        public void tokenError() {
                            lisDidNotRegister();
                        }
                    };
                    subscribeToken.request(token);
                }

                @Override
                public void didFailToGetFirebaseToken() {
                    lisDidNotRegister();
                }

                @Override
                public void didFailBecauseFirebaseIsNotSetup() {
                    lisDidNotRegister();
                }
            };
            firebaseGetToken.register();
        }
    }

    public void unregisterForRemoteNotifications () {
        String token = pushRegister.getToken();
        unsubscribeToken.listener = new KWSUnsubscribeTokenInterface() {
            @Override
            public void tokenWasUnsubscribed() {
                Log.d("SuperAwesome", "Token unsubscribed");
            }

            @Override
            public void tokenError() {
                Log.d("SuperAwesome", "Token failed to be unsubscribed");
            }
        };
        unsubscribeToken.request(token);
    }

    // <Private> functions

    private boolean checkPlayServices() {
        Context context = SAApplication.getSAApplicationContext();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }

    // <Listener> functions

    void lisDidRegister (String token) {
        if (listener != null) {
            listener.didRegister(token);
        }
    }

    void lisDidNotRegister () {
        if (listener != null) {
            listener.didNotRegister();
        }
    }
}

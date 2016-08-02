package kws.superawesome.tv.kwssdk.managers;

import android.content.Context;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.firebase.iid.FirebaseInstanceId;

import tv.superawesome.lib.sautils.SAApplication;

import kws.superawesome.tv.kwssdk.kws.KWSSubscribeToken;
import kws.superawesome.tv.kwssdk.kws.KWSSubscribeTokenInterface;
import kws.superawesome.tv.kwssdk.kws.KWSUnsubscribeToken;
import kws.superawesome.tv.kwssdk.kws.KWSUnsubscribeTokenInterface;
import kws.superawesome.tv.kwssdk.firebase.FirebaseGetToken;
import kws.superawesome.tv.kwssdk.firebase.FirebaseGetTokenInterface;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class PushManager {

    // singleton instance
    public static PushManager sharedInstance = new PushManager();

    // private variables
    private FirebaseGetToken firebaseGetToken = null;
    private KWSSubscribeToken subscribeToken = null;
    private KWSUnsubscribeToken unsubscribeToken = null;

    // listener
    public PushManagerInterface listener = null;

    // private constructor
    private PushManager () {
        firebaseGetToken = new FirebaseGetToken();
        subscribeToken = new KWSSubscribeToken();
        unsubscribeToken = new KWSUnsubscribeToken();
    }

    // public function

    public void registerForPushNotifications () {

        if (!checkPlayServices()) {
            lisDidFailBecauseNoGoogleServicesFound();
        }
        else {

            firebaseGetToken.listener = new FirebaseGetTokenInterface() {
                @Override
                public void didGetFirebaseToken(final String token) {
                    subscribeToken.listener = new KWSSubscribeTokenInterface() {
                        @Override
                        public void tokenWasSubscribed() {
                            lisDidRegister(token);
                        }

                        @Override
                        public void tokenSubscribeError() {
                            lisNetworkErrorTryingToSubscribeToken();
                        }
                    };
                    subscribeToken.execute(token);
                }

                @Override
                public void didFailToGetFirebaseToken() {
                    lisDidFailToGetFirebaseToken();
                }
            };
            firebaseGetToken.register();
        }
    }

    public void unregisterForRemoteNotifications () {
        unsubscribeToken.listener = new KWSUnsubscribeTokenInterface() {
            @Override
            public void tokenWasUnsubscribed() {
                lisDidUnregister();
            }

            @Override
            public void tokenUnsubscribeError() {
                lisNetworkErrorTryingToUnsubscribeToken();
            }
        };
        String token = firebaseGetToken.getSavedToken();
        unsubscribeToken.execute(token);
    }

    // <Private> functions

    private boolean checkPlayServices() {
//        Context context = SAApplication.getSAApplicationContext();
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
//        return resultCode == ConnectionResult.SUCCESS;
        return true;
    }

    // <Listener> functions

    void lisDidRegister (String token) {
        if (listener != null) {
            listener.didRegister(token);
        }
    }

    void lisDidUnregister () {
        if (listener != null) {
            listener.didUnregister();
        }
    }

    void lisDidFailBecauseNoGoogleServicesFound () {
        if (listener != null) {
            listener.didFailBecauseNoGoogleServicesFound ();
        }
    }

    void lisDidFailToGetFirebaseToken () {
        if (listener != null) {
            listener.didFailToGetFirebaseToken();
        }
    }

    void lisNetworkErrorTryingToSubscribeToken () {
        if (listener != null) {
            listener.networkErrorTryingToSubscribeToken();
        }
    }

    void lisNetworkErrorTryingToUnsubscribeToken () {
        if (listener != null) {
            listener.networkErrorTryingToUnsubscribeToken();
        }
    }
}

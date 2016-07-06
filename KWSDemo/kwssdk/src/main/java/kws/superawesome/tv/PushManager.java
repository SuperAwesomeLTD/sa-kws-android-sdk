package kws.superawesome.tv;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

import tv.superawesome.lib.sautils.SAApplication;

import kws.superawesome.tv.kws.KWSSubscribeToken;
import kws.superawesome.tv.kws.KWSSubscribeTokenInterface;
import kws.superawesome.tv.kws.KWSUnsubscribeToken;
import kws.superawesome.tv.kws.KWSUnsubscribeTokenInterface;
import kws.superawesome.tv.firebase.FirebaseGetToken;
import kws.superawesome.tv.firebase.FirebaseGetTokenInterface;

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
            lisDidNotRegister();
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
        unsubscribeToken.listener = new KWSUnsubscribeTokenInterface() {
            @Override
            public void tokenWasUnsubscribed() {
                lisDidUnregister();
            }

            @Override
            public void tokenError() {
                lisDidNotUnregister();
            }
        };
        String token = firebaseGetToken.getSavedToken();
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

    void lisDidUnregister () {
        if (listener != null) {
            listener.didUnregister();
        }
    }

    void lisDidNotUnregister () {
        if (listener != null) {
            listener.didNotUnregister();
        }
    }
}

package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import kws.superawesome.tv.kwslib.SAApplication;
import kws.superawesome.tv.kwssdk.kws.KWSSubscribeToken;
import kws.superawesome.tv.kwssdk.kws.KWSSubscribeTokenInterface;
import kws.superawesome.tv.kwssdk.push.KWSRegistrationService;
import kws.superawesome.tv.kwssdk.push.KWSRegistrationServiceInterface;
import kws.superawesome.tv.kwssdk.push.PushRegisterPermission;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class PushManager {

    // singleton instance
    public static PushManager sharedInstance = new PushManager();

    // private variables
    private PushRegisterPermission pushRegister = null;
    private KWSRegistrationService kwsRegistration = null;
    private KWSSubscribeToken updateToken = null;

    // listener
    public PushManagerInterface listener = null;

    // private constructor
    private PushManager () {
        kwsRegistration = new KWSRegistrationService();
        pushRegister = new PushRegisterPermission();
        updateToken = new KWSSubscribeToken();
    }

    // public function

    public void registerForPushNotifications () {

        if (!checkPlayServices()) {
            lisDidNotRegister();
        }
        else {
            kwsRegistration.listener = new KWSRegistrationServiceInterface() {
                @Override
                public void didGetToken(String token) {

                    Log.d("SuperAwesome", "Token is " + token);

                    pushRegister.register(token);
                    updateToken.listener = new KWSSubscribeTokenInterface() {
                        @Override
                        public void tokenWasSubscribed() {
                            Log.d("SuperAwesome", "Token was successfully updated");
                            lisDidRegister();
                        }

                        @Override
                        public void tokenError() {
                            lisDidNotRegister();
                        }
                    };
                    updateToken.request(token);
                }

                @Override
                public void didNotGetToken() {
                    lisDidNotRegister();
                }
            };
            kwsRegistration.register();
        }
    }

    // <Private> functions

    private boolean checkPlayServices() {
        Context context = SAApplication.getSAApplicationContext();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    // <Listener> functions

    void lisDidRegister () {
        if (listener != null) {
            listener.didRegister();
        }
    }

    void lisDidNotRegister () {
        if (listener != null) {
            listener.didNotRegister();
        }
    }
}

package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import kws.superawesome.tv.kwslib.SAApplication;
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

    // listener
    public PushManagerInterface listener = null;

    // private constructor
    private PushManager () {
        pushRegister = new PushRegisterPermission();
    }

    // public function

    public void registerForPushNotifications () {

        Context context = SAApplication.getSAApplicationContext();

        if (!checkPlayServices()) {
            lisDidNotRegister();
        }
        else {
            kwsRegistration = new KWSRegistrationService();
            kwsRegistration.listener = new KWSRegistrationServiceInterface() {
                @Override
                public void didGetToken(String token) {
                    pushRegister.register(token);
                    Log.d("SuperAwesome", "Toke is " + token);
                    lisDidRegister();
                }

                @Override
                public void didNotGetToken() {
                    lisDidNotRegister();
                }
            };
            kwsRegistration.register(context);
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

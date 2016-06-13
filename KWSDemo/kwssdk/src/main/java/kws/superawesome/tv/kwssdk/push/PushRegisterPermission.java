package kws.superawesome.tv.kwssdk.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import kws.superawesome.tv.kwslib.SAApplication;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class PushRegisterPermission {

    // consants
    private static final String PREFERENCES = "MyPreferences";
    private static final String kPushToken = "KWSPushToken";

    // private vars
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // listener
    public PushRegisterPermissionInterface listener = null;

    // constructor
    public PushRegisterPermission(){
        Context context = SAApplication.getSAApplicationContext();
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // main class func
    public void isRegistered (){
        String token = sharedPreferences.getString(kPushToken, null);

        Log.d("SuperAwesome", "What's the token " + token);

        if (token != null) {
            lisIsRegisteredInSystem();
        }
        else {
            lisIsNotRegisteredInSystem();
        }
    }

    public String getToken () {
        return sharedPreferences.getString(kPushToken, null);
    }

    public void register(String token) {
        editor.putString(kPushToken, token);
        editor.apply();
    }

    public void unregister() {
        editor.remove(kPushToken);
        editor.apply();
    }

    // listener funcs
    void lisIsRegisteredInSystem () {
        if (listener != null) {
            listener.isRegisteredInSystem();
        }
    }

    void lisIsNotRegisteredInSystem () {
        if (listener != null) {
            listener.isNotRegisteredInSystem();
        }
    }
}

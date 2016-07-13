package kws.superawesome.tv.kwssdk.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public class FirebaseGetToken extends FirebaseInstanceIdService {

    // constants
    private final int NR_TRIES = 30;
    private final int DELAY = 1000;

    // listener
    public FirebaseGetTokenInterface listener = null;

    // privates
    private Handler handler = new Handler();
    private Runnable runnable = null;
    private int tries = 0;

    public FirebaseGetToken () {
        Context context = SAApplication.getSAApplicationContext();
    }

    public void register() {

        runnable = new Runnable() {
            @Override
            public void run() {
                String token = FirebaseInstanceId.getInstance().getToken();

                if (token != null) {
                    lisDidGetFirebaseToken(token);
                } else if (tries > NR_TRIES) {
                    lisDidFailToGetFirebaseToken();
                } else {
                    tries++;
                    handler.postDelayed(this, DELAY);
                }
            }
        };
        handler.postDelayed(runnable, DELAY);
    }

    public String getSavedToken () {
        return FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
    }

    // <Private> functions

    void lisDidGetFirebaseToken(String token) {
        if (listener != null) {
            listener.didGetFirebaseToken(token);
        }
    }

    void lisDidFailToGetFirebaseToken() {
        if (listener != null) {
            listener.didFailToGetFirebaseToken();
        }
    }
}

package kws.superawesome.tv.kwssdk.push;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import kws.superawesome.tv.kwssdk.KWS;

/**
 * Created by gabriel.coman on 24/05/16.
 */

public class KWSRegistrationService extends FirebaseInstanceIdService {


    // listener
    public KWSRegistrationServiceInterface listener = null;

    // privates
    private Handler handler = new Handler();
    private Runnable runnable = null;
    private int tries = 0;
    private final int NR_TRIES = 5;

    public void register() {

        runnable = new Runnable() {
            @Override
            public void run() {
                String token = FirebaseInstanceId.getInstance().getToken();

                if (token != null) {
                    // FirebaseMessaging.getInstance().subscribeToTopic("movies");
                    lisDidGetToken(token);
                } else if (tries > NR_TRIES) {
                    lisDidNotGetToken();
                } else {
                    tries++;
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
    }

    // <Private> functions

    void lisDidGetToken(String token) {
        if (listener != null) {
            listener.didGetToken(token);
        }
    }

    void lisDidNotGetToken() {
        if (listener != null) {
            listener.didNotGetToken();
        }
    }
}


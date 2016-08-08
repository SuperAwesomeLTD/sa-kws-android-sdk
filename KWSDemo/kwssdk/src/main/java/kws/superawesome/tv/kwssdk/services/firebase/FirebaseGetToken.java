package kws.superawesome.tv.kwssdk.services.firebase;

import android.os.Handler;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public class FirebaseGetToken extends FirebaseInstanceIdService {

    // constants
    private final int NR_TRIES = 30;
    private final int DELAY = 1000;

    // listener
    private FirebaseGetTokenInterface listener = null;

    // privates
    private Handler handler = new Handler();
    private Runnable runnable = null;
    private int tries = 0;

    public void execute(final FirebaseGetTokenInterface listener) {
        FirebaseGetTokenInterface local = new FirebaseGetTokenInterface() {public void gotToken(boolean success, String token) {}};
        this.listener = listener != null ? listener : local;

        runnable = new Runnable() {
            @Override
            public void run() {
                String token = FirebaseInstanceId.getInstance().getToken();

                if (token != null) {
                    FirebaseGetToken.this.listener.gotToken(true, token);
                } else if (tries > NR_TRIES) {
                    FirebaseGetToken.this.listener.gotToken(false, null);
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
}

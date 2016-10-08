package kws.superawesome.tv.kwssdk.services.firebase;

import android.os.Handler;

import java.lang.reflect.InvocationTargetException;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public class FirebaseGetToken {

    // constants
    private final int NR_TRIES = 30;
    private final int DELAY = 1000;

    // listener
    private FirebaseGetTokenInterface listener = null;

    // privates
    private Handler handler = new Handler();
    private Runnable runnable = null;
    private int tries = 0;

    public FirebaseGetToken () {
        this.listener = new FirebaseGetTokenInterface() { @Override public void gotToken(boolean success, String token) {} };
    }

    public void execute(final FirebaseGetTokenInterface listener) {
        this.listener = listener != null ? listener : this.listener;

        if (SAUtils.isClassAvailable("com.google.firebase.iid.FirebaseInstanceId")) {

            runnable = new Runnable() {
                @Override
                public void run() {

                    String token = getToken();

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

        } else {
            if (listener != null) {
                listener.gotToken(false, null);
            }
        }
    }

    private String getToken () {
        String token = null;

        try {
            Class<?> firebase = Class.forName("com.google.firebase.iid.FirebaseInstanceId");
            java.lang.reflect.Method method = firebase.getMethod("getInstance");
            Object instance = method.invoke(firebase);
            java.lang.reflect.Method method1 = firebase.getMethod("getToken");
            Object returnValue = method1.invoke(instance);
            token = (String) returnValue;

        } catch (ClassNotFoundException e) {
            // failure
        } catch (NoSuchMethodException e) {
            // failure
        } catch (InvocationTargetException e) {
            // failure
        } catch (IllegalAccessException e) {
            // failure;
        }

        return token;
    }

    public String getSavedToken () {
        return getToken();
    }
}

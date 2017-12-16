package kws.superawesome.tv.kwssdk.base.webauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

public class KWSWebAuthResponse extends Activity {

    public static Interface listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        //
        // get the action
        String action = null;
        try {
            action = intent.getAction();
        } catch (Exception e) {
            // do nothing
        }

        //
        // get the data
        Uri data = null;
        try {
            data = intent.getData();
        } catch (Exception e) {
            // do nothing
        }

        //
        // get the scheme
        String scheme = null;
        try {
            if (data != null) {
                scheme = data.getScheme();
            }
        } catch (Exception e) {
            // do nothing
        }

        //
        // get the package name
        String packName = this.getPackageName();

        if (data != null && action != null && scheme != null && action.equals(Intent.ACTION_VIEW) && scheme.equals(packName)) {

            //
            // get fragment
            String fragment = data.getFragment();

            //
            // split fragment by parts
            String[] parts;
            try {
                parts = TextUtils.split(fragment, "=");
            } catch (Exception e) {
                parts = null;
            }

            //
            // get token
            String token = null;
            if (parts != null && parts.length >= 2) {
                token = parts[1];
            }

            //
            // callback
            if (listener != null) {
                listener.didGetToken(token);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.finish();
    }

    public interface Interface {
        void didGetToken (String token);
    }
}

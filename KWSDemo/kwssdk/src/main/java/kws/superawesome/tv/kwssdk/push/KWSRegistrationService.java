package kws.superawesome.tv.kwssdk.push;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import kws.superawesome.tv.kwssdk.KWS;

/**
 * Created by gabriel.coman on 24/05/16.
 */

public class KWSRegistrationService {

    // constants
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    // private data
    private KWSRegistrationServiceReceiver receiver = null;

    // listener
    public KWSRegistrationServiceInterface listener = null;

    // Main function
    public void register (Context c) {

        // receive data
        receiver = new KWSRegistrationServiceReceiver(new Handler());
        receiver.setReceiver(new KWSRegistrationServiceReceiverInterface() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {

                String token = resultData.getString("token");
                switch (resultCode) {
                    case STATUS_FINISHED:lisDidGetToken(token);break;
                    case STATUS_ERROR:lisDidNotGetToken();break;
                }
            }
        });

        // send data
        Intent intent = new Intent(c, KWSRegistrationIntentService.class);
        intent.putExtra("receiver", receiver);
        c.startService(intent);
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

    public static class KWSRegistrationIntentService extends IntentService {

        private static final String TAG = "KWSRegistrationIntent";

        public KWSRegistrationIntentService() {
            super(TAG);
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            final ResultReceiver receiver = intent.getParcelableExtra("receiver");

            try {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(KWS.sdk.gcmSender, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                receiver.send(STATUS_FINISHED, bundle);

            } catch (Exception e) {
                receiver.send(STATUS_ERROR, Bundle.EMPTY);
            }
        }
    }
}

class KWSRegistrationServiceReceiver extends ResultReceiver {
    private KWSRegistrationServiceReceiverInterface mReceiver;

    public KWSRegistrationServiceReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(KWSRegistrationServiceReceiverInterface receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}

interface KWSRegistrationServiceReceiverInterface {
    void onReceiveResult(int resultCode, Bundle resultData);
}


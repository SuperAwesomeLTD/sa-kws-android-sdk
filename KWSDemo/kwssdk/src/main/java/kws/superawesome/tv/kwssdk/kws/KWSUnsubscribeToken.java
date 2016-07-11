package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sanetwork.request.*;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 13/06/16.
 */
public class KWSUnsubscribeToken {

    // listener
    public KWSUnsubscribeTokenInterface listener = null;

    public void request (String token) {

        Log.d("SuperAwesome", "Removing token from KWS: " + token);

        String kwsApiUrl = KWS.sdk.getKwsApiUrl();
        String oauthToken = KWS.sdk.getOauthToken();
        KWSMetadata metadata = KWS.sdk.getMetadata();

        if (kwsApiUrl != null && oauthToken != null && metadata != null){

            // form the request
            int userId = metadata.userId;
            int appId = metadata.appId;
            String endpoint = kwsApiUrl + "apps/" + appId + "/users/" + userId + "/unsubscribe-push-notifications";
            JSONObject body = new JSONObject();
            try {
                body.put("token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject header = new JSONObject();
            try {
                header.put("Authorization", "Bearer " + oauthToken);
                header.put("Content-Type", "application/json");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // send the network request
            SANetwork network = new SANetwork();
            network.sendPOST(SAApplication.getSAApplicationContext(), endpoint, new JSONObject(), header, body, new SANetworkInterface() {
                @Override
                public void success(int status, String payload) {
                    Log.d("SuperAwesome", "Payload ==> " + payload);
                    lisTokenWasUnsubscribed();
                }

                @Override
                public void failure() {
                    lisTokenError();
                }
            });

        } else {
            lisTokenError();
        }
    }

    // <Private> functions

    private void lisTokenWasUnsubscribed () {
        if (listener != null) {
            listener.tokenWasUnsubscribed();
        }
    }

    private void lisTokenError () {
        if (listener != null) {
            listener.tokenUnsubscribeError();
        }
    }
}

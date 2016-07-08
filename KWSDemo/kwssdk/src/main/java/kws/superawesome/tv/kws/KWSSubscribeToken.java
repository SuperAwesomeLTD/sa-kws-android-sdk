package kws.superawesome.tv.kws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sanetwork.request.*;

import kws.superawesome.tv.KWS;
import kws.superawesome.tv.models.KWSMetadata;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 13/06/16.
 */
public class KWSSubscribeToken {

    // listener
    public KWSSubscribeTokenInterface listener = null;

    // the actual request
    public void request (String token) {
        String kwsApiUrl = KWS.sdk.getKwsApiUrl();
        String oauthToken = KWS.sdk.getOauthToken();
        KWSMetadata metadata = KWS.sdk.getMetadata();

        if (kwsApiUrl != null && oauthToken != null && metadata != null){

            Log.d("SuperAwesome", "Adding token to KWS: " + token);

            // form the request
            int userId = metadata.userId;
            int appId = metadata.appId;
            String endpoint = kwsApiUrl + "apps/" + appId + "/users/" + userId + "/subscribe-push-notifications";
            JSONObject body = new JSONObject();
            try {
                body.put("token", token);
                body.put("platform", "android");
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
                    lisTokenWasSubsribed();
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

    private void lisTokenWasSubsribed () {
        if (listener != null) {
            listener.tokenWasSubscribed();
        }
    }

    private void lisTokenError () {
        if (listener != null) {
            listener.tokenSubscribeError();
        }
    }
}

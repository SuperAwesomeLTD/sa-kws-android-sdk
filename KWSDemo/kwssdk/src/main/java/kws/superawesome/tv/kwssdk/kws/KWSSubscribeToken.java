package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwslib.SANetwork;
import kws.superawesome.tv.kwslib.SANetworkInterface;
import kws.superawesome.tv.kwslib.SANetworkResponse;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;

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

            // form the request
            int userId = metadata.userId;
            int appId = metadata.appId;
            String endpoint = kwsApiUrl + "apps/" + appId + "/users/" + userId + "/subscribe-push-notifications";
            JSONObject body = new JSONObject();
            try {
                body.put("token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // send the network request
            SANetwork network = new SANetwork();
            network.sendPOST(endpoint, oauthToken, body, new SANetworkInterface() {
                @Override
                public void success(Object data) {

                    SANetworkResponse response = (SANetworkResponse)data;
                    Log.d("SuperAwesome", "Payload ==> " + response.payload);
                    lisTokenWasUpdated();
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

    private void lisTokenWasUpdated () {
        if (listener != null) {
            listener.tokenWasSubscribed();
        }
    }

    private void lisTokenError () {
        if (listener != null) {
            listener.tokenError();
        }
    }
}

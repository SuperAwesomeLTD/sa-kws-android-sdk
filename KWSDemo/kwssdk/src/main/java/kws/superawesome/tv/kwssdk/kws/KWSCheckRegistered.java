package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 14/07/16.
 */
public class KWSCheckRegistered {

    // listener
    public KWSCheckRegisteredInterface listener = null;

    /**
     * Main check function
     */
    public void check () {
        String kwsApiUrl = KWS.sdk.getKwsApiUrl();
        String oauthToken = KWS.sdk.getOauthToken();
        KWSMetadata metadata = KWS.sdk.getMetadata();

        if (kwsApiUrl != null && oauthToken != null && metadata != null){

            // form the request
            int userId = metadata.userId;
            int appId = metadata.appId;
            String endpoint = kwsApiUrl + "apps/" + appId + "/users/" + userId + "/has-device-token";

            JSONObject header = new JSONObject();
            try {
                header.put("Authorization", "Bearer " + oauthToken);
                header.put("Content-Type", "application/json");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SANetwork network = new SANetwork();
            network.sendGET(SAApplication.getSAApplicationContext(), endpoint, new JSONObject(), header, new SANetworkInterface() {
                @Override
                public void success(int status, String payload) {
                    if (payload.contentEquals("true")) {
                        lisUserIsRegistered();
                    } else if (payload.contentEquals("false")) {
                        lisUserIsNotRegistered();
                    } else {
                        lisCheckRegisteredError();
                    }
                }

                @Override
                public void failure() {
                    lisCheckRegisteredError();
                }
            });


        } else {
            lisCheckRegisteredError();
        }
    }

    // Listener functions

    void lisUserIsRegistered () {
        if (listener != null) {
            listener.userIsRegistered();
        }
    }

    void lisUserIsNotRegistered () {
        if (listener != null) {
            listener.userIsNotRegistered();
        }
    }

    void lisCheckRegisteredError () {
        if (listener != null) {
            listener.checkRegisteredError();
        }
    }
}

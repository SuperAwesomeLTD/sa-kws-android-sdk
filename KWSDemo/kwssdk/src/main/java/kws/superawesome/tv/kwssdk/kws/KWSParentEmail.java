package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwslib.SANetwork;
import kws.superawesome.tv.kwslib.SANetworkInterface;
import kws.superawesome.tv.kwslib.SANetworkResponse;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSError;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSParentEmail {

    // public listener
    public KWSParentEmailInterface listener = null;

    /**
     * Main class function
     * @param email email to send
     */
    public void submitEmail(String email) {

        if (KWS.sdk.kwsApiUrl != null && KWS.sdk.oauthToken != null && KWS.sdk.metadata != null) {

            int userId= KWS.sdk.metadata.userId;
            String endpoint = KWS.sdk.kwsApiUrl + "users/" + userId + "/request-permissions";
            JSONObject body = new JSONObject();
            JSONArray array = new JSONArray();
            array.put("sendPushNotification");
            try {
                body.put("permissions", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SANetwork network = new SANetwork();
            network.sendPOST(endpoint, KWS.sdk.oauthToken, body, new SANetworkInterface() {
                @Override
                public void success(Object data) {

                    SANetworkResponse response = (SANetworkResponse)data;
                    int status = response.statusCode;

                    if (status == 200 || status == 204) {
                        lisEmailSubmittedInKWS();
                    }
                    else {
                        lisEmailError();
                    }
                }

                @Override
                public void failure() {
                    lisEmailError();
                }
            });
        }

    }

    // <Private> functions

    void lisEmailSubmittedInKWS () {
        if (listener != null) {
            listener.emailSubmittedInKWS();
        }
    }

    void lisEmailError () {
        if (listener != null) {
            listener.emailError();
        }
    }
}

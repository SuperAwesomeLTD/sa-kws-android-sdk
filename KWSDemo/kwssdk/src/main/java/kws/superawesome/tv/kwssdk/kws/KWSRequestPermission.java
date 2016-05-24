package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Deque;

import kws.superawesome.tv.kwslib.SANetwork;
import kws.superawesome.tv.kwslib.SANetworkInterface;
import kws.superawesome.tv.kwslib.SANetworkResponse;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.*;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSRequestPermission {

    // public listener
    public KWSRequestPermissionInterface listener = null;

    /**
     Main function of the class
     */
    public void request () {

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

                    Log.d("SuperAwesome", "Payload ==> " + response.payload);

                    /** form the error - if exists */
                    KWSError error = null;
                    try {
                        JSONObject json = new JSONObject(response.payload);
                        error = new KWSError(json);
                    } catch (JSONException e) {
                        // do nothing - 'cause it's ok if no data is there
                    }

                    /** goto branches */
                    if (status == 200 || status == 204) {
                        lisPushPermissionRequestedInKWS();
                    }
                    else if (status != 204 && error != null) {

                        if (error.code == 5 && error.invalid != null && error.invalid.parentEmail != null && error.invalid.parentEmail.code == 6) {
                            lisParentEmailIsMissingInKWS();
                        }
                        else {
                            lisPequestError();
                        }
                    }
                    else {
                        lisPequestError();
                    }

                }

                @Override
                public void failure() {
                    lisPequestError();
                }
            });
        }

    }

    // <Private> functions

    private void lisPushPermissionRequestedInKWS () {
        if (listener != null) {
            listener.pushPermissionRequestedInKWS();
        }
    }

    private void lisParentEmailIsMissingInKWS () {
        if (listener != null) {
            listener.parentEmailIsMissingInKWS();
        }
    }

    private void lisPequestError () {
        if (listener != null) {
            listener.requestError();
        }
    }
}

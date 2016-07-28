package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.error.KWSError;
import tv.superawesome.lib.sanetwork.request.*;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.*;
import tv.superawesome.lib.sautils.SAApplication;

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

        String kwsApiUrl = KWS.sdk.getKwsApiUrl();
        String oauthToken = KWS.sdk.getOauthToken();
        KWSMetadata metadata = KWS.sdk.getMetadata();
        String version = KWS.sdk.getVersion();

        if (kwsApiUrl != null && oauthToken != null && metadata != null){

            int userId= metadata.userId;
            String endpoint = kwsApiUrl + "users/" + userId + "/request-permissions";
            JSONObject body = new JSONObject();
            JSONArray array = new JSONArray();
            array.put("sendPushNotification");
            try {
                body.put("permissions", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject header = new JSONObject();
            try {
                header.put("Authorization", "Bearer " + oauthToken);
                header.put("Content-Type", "application/json");
                header.put("kws-sdk-version", version);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SANetwork network = new SANetwork();
            network.sendPOST(SAApplication.getSAApplicationContext(), endpoint, new JSONObject(), header, body, new SANetworkInterface() {
                @Override
                public void success(int status, String payload) {
                    Log.d("SuperAwesome", "Payload ==> " + payload);

                    /** form the error - if exists */
                    KWSError error = null;
                    try {
                        JSONObject json = new JSONObject(payload);
                        error = new KWSError(json);
                    } catch (JSONException e) {
                        // do nothing - 'cause it's ok if no data is there
                    }

                    /** goto branches */
                    if (status == 200 || status == 204) {
                        lisPushPermissionRequestedInKWS();
                    }
                    else if (error != null) {

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
            listener.permissionError();
        }
    }
}

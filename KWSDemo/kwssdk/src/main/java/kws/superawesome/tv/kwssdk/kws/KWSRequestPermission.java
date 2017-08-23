package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.error.KWSError;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.*;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.*;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSRequestPermission extends KWSRequest {

    // public listener
    public KWSRequestPermissionInterface listener = null;

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.metadata.userId + "/request-permissions";
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[]{
                "permissions", SAJsonParser.newArray(new Object[]{
                    "sendPushNotification"
            })
        });
    }

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

            if (error.code == 10 && error.invalid != null && error.invalid.parentEmail != null && error.invalid.parentEmail.code == 6) {
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

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
public class KWSCheckRegistered extends KWSRequest {

    // listener
    public KWSCheckRegisteredInterface listener = null;

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.metadata.appId + "/users/" + super.metadata.userId + "/has-device-token";
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.GET;
    }

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

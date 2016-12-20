package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.*;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 13/06/16.
 */
public class KWSUnsubscribeToken extends KWSRequest {

    // listener
    public KWSUnsubscribeTokenInterface listener = null;

    // private
    private String token;

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.metadata.appId + "/users/" + super.metadata.userId + "/unsubscribe-push-notifications";
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[]{
                "token", token
        });
    }

    @Override
    public void success(int status, String payload) {
        Log.d("SuperAwesome", "Payload ==> " + payload);
        lisTokenWasUnsubscribed();
    }

    @Override
    public void failure() {
        lisTokenError();
    }

    @Override
    public void execute(Object param) {

        // check param
        if (param instanceof String) {
            token = (String)param;
        } else {
            lisTokenError();
            return;
        }

        // execute
        super.execute(param);
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

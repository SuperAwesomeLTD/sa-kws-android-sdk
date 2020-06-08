package kws.superawesome.tv.kwssdk.kws;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 13/06/16.
 */
public class KWSSubscribeToken extends KWSRequest {

    // listener
    public KWSSubscribeTokenInterface listener = null;

    // private vars
    private String token = null;

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.metadata.appId + "/users/" + super.metadata.userId + "/subscribe-push-notifications";
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return KWSJsonParser.newObject(new Object[]{
                "token", token,
                "platform", "android"
        });
    }

    @Override
    public void success(int status, String payload) {
        Log.d("SuperAwesome", "Payload ==> " + payload);
        lisTokenWasSubsribed();
    }

    @Override
    public void failure() {
        lisTokenError();
    }

    @Override
    public void execute(Context context, Object param) {
        // check for param
        if (param instanceof  String) {
            token = (String)param;
        } else {
            lisTokenError();
            return;
        }

        // execute
        super.execute(context, param);
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

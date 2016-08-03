package kws.superawesome.tv.kwssdk.services.kws;

import android.util.Log;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 13/06/16.
 */
public class KWSRegisterToken extends KWSService {

    // listener
    private KWSRegisterTokenInterface listener = null;

    // private vars
    private String token = null;

    @Override
    public String getEndpoint() {
        return "apps/" + super.metadata.appId + "/users/" + super.metadata.userId + "/subscribe-push-notifications";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[]{
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
    public void execute(Object param, KWSServiceResponseInterface listener) {

        this.listener = (KWSRegisterTokenInterface) listener;

        // check for param
        if (param instanceof  String) {
            token = (String)param;
        } else {
            lisTokenError();
            return;
        }

        // execute
        super.execute(param, this.listener);
    }

    private void lisTokenWasSubsribed () {
        if (listener != null) {
            listener.registered(true);
        }
    }

    private void lisTokenError () {
        if (listener != null) {
            listener.registered(false);
        }
    }
}

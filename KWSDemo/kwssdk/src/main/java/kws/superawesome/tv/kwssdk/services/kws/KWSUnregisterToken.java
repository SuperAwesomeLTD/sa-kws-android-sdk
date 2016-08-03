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
public class KWSUnregisterToken extends KWSService {

    // listener
    private KWSUnregisterTokenInterface listener = null;

    // private
    private String token;

    @Override
    public String getEndpoint() {
        return "apps/" + super.metadata.appId + "/users/" + super.metadata.userId + "/unsubscribe-push-notifications";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
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
    public void execute(Object param, KWSServiceResponseInterface listener) {

        this.listener = (KWSUnregisterTokenInterface) listener;

        // check param
        if (param instanceof String) {
            token = (String)param;
        } else {
            lisTokenError();
            return;
        }

        // execute
        super.execute(param, this.listener);
    }

    // <Private> functions

    private void lisTokenWasUnsubscribed () {
        if (listener != null) {
            listener.unregistered(true);
        }
    }

    private void lisTokenError () {
        if (listener != null) {
            listener.unregistered(false);
        }
    }
}

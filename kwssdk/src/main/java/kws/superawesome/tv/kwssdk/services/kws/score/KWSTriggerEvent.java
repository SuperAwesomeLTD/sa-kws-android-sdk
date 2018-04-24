package kws.superawesome.tv.kwssdk.services.kws.score;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 08/08/16.
 */
public class KWSTriggerEvent extends KWSService{

    // private vars
    private KWSChildrenTriggerEventInterface listener = null;
    private String evtToken = null;
    private int evtPoints = 0;

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.loggedUser.metadata.userId + "/trigger-event";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        JSONObject body = new JSONObject();
        try {
            body.put("points", evtPoints);
        } catch (JSONException ignored) {}
        if (evtToken != null) {
            try {
                body.put("token", evtToken);
            } catch (JSONException ignored) {}
        }
        return body;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        listener.didTriggerEvent(success && (status == 200 || status == 204));
    }

    public void execute(Context context, String token, int points, KWSServiceResponseInterface listener) {
        // getService vars
        this.evtToken = token;
        this.evtPoints = points;

        // getService listener
        this.listener = listener != null ? (KWSChildrenTriggerEventInterface) listener : new KWSChildrenTriggerEventInterface() { public void didTriggerEvent(boolean success) {}};

        // execute
        super.execute(context, this.listener);
    }
}

package kws.superawesome.tv.kwssdk.services.kws;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceInterface;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 08/08/16.
 */
public class KWSTriggerEvent extends KWSService{

    // private vars
    private KWSTriggerEventInterface listener = null;
    private String evtToken = null;
    private int evtPoints = 0;
    private String evtDescription = null;

    @Override
    public String getEndpoint() {
        return "users/" + super.metadata.userId + "/trigger-event";
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
        if (evtDescription != null && !evtDescription.equals("")) {
            try {
                body.put("description", evtDescription);
            } catch (JSONException ignored) {}
        }
        return body;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        listener.triggered(success && (status == 200 || status == 204));
    }

    public void execute(String token, int points, String description, KWSServiceResponseInterface listener) {
        // get vars
        this.evtToken = token;
        this.evtPoints = points;
        this.evtDescription = description;

        // get listener
        this.listener = listener != null ? (KWSTriggerEventInterface) listener : new KWSTriggerEventInterface() { public void triggered(boolean success) {}};

        // execute
        super.execute(this.listener);
    }
}

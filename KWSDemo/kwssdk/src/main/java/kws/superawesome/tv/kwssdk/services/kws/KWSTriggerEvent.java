package kws.superawesome.tv.kwssdk.services.kws;

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
        return SAJsonParser.newObject(new Object[]{
                "token", evtToken,
                "points", evtPoints,
                "description", evtDescription
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.triggered(false);
        } else {
            if (status == 200 || status == 204) {
                listener.triggered(true);
            } else {
                listener.triggered(false);
            }
        }
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

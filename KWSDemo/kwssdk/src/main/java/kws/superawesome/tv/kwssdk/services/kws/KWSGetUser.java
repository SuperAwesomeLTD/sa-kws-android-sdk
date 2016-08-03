package kws.superawesome.tv.kwssdk.services.kws;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public class KWSGetUser extends KWSService {

    // private user
    private KWSGetUserInterface listener = null;

    @Override
    public String getEndpoint() {
        return "users/" + super.metadata.userId;
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload) {
        if ((status == 200 || status == 204) && payload != null) {
            JSONObject json = SAJsonParser.newObject(payload);
            KWSUser user = new KWSUser(json);
            lisGotUserOK(user);
        } else {
            lisGotUserNOK();
        }
    }

    @Override
    public void failure() {
        lisGotUserNOK();
    }

    @Override
    public void execute(KWSServiceResponseInterface listener) {
        this.listener = (KWSGetUserInterface) listener;
        super.execute(this.listener);
    }

    // listener methods
    private void lisGotUserOK (KWSUser user) {
        if (listener != null) {
            listener.gotUser(user);
        }
    }

    private void lisGotUserNOK () {
        if (listener != null) {
            listener.gotUser(null);
        }
    }
}

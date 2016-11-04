package kws.superawesome.tv.kwssdk.services.kws;

import android.content.Context;

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

    private KWSGetUserInterface listener = null;

    public KWSGetUser () {
        listener = new KWSGetUserInterface() { @Override public void gotUser(KWSUser user) {} };
    }

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.loggedUser.metadata.userId;
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.gotUser(null);
        } else {
            if ((status == 200 || status == 204) && payload != null) {
                JSONObject json = SAJsonParser.newObject(payload);
                KWSUser user = new KWSUser(json);
                listener.gotUser(user);
            } else {
                listener.gotUser(null);
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSGetUserInterface) listener : this.listener;
        super.execute(context, this.listener);
    }
}

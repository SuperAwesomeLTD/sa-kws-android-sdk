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
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSCheckAllowed extends KWSService {

    // listener interface
    private KWSCheckAllowedInterface listener = null;

    public KWSCheckAllowed () {
        listener = new KWSCheckAllowedInterface() { @Override public void allowed(boolean allowed) {} };
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
            listener.allowed(false);
        } else {
            if (status == 200 || status == 204) {
                JSONObject json = SAJsonParser.newObject(payload);
                KWSUser user = new KWSUser(json);
                Object perm = user.applicationPermissions.sendPushNotification;

                if (perm == null || (boolean) perm) {
                    listener.allowed(true);
                } else {
                    listener.allowed(false);
                }

            } else {
                listener.allowed(false);
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        this.listener =  listener != null ? (KWSCheckAllowedInterface) listener : this.listener;
        super.execute(context, this.listener);
    }
}

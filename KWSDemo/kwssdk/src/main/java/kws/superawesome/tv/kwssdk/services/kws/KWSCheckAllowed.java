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

    @Override
    public String getEndpoint() {
        return "users/" + super.metadata.userId;
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.allowed(false, false);
        } else {
            if (status == 200 || status == 204) {
                JSONObject json = SAJsonParser.newObject(payload);
                KWSUser user = new KWSUser(json);
                Object perm = user.applicationPermissions.sendPushNotification;

                if (perm == null || (boolean) perm) {
                    listener.allowed(true, true);
                } else {
                    listener.allowed(true, false);
                }

            } else {
                listener.allowed(false, false);
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        KWSCheckAllowedInterface local = new KWSCheckAllowedInterface() { public void allowed(boolean success, boolean allowed) {}};
        this.listener =  (listener != null ? (KWSCheckAllowedInterface) listener : local);
        super.execute(context, this.listener);
    }
}

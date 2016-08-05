package kws.superawesome.tv.kwssdk.services.kws;

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
    public void success(int status, String payload) {
        if (status == 200 || status == 204) {

            JSONObject json = SAJsonParser.newObject(payload);
            KWSUser user = new KWSUser(json);
            Object perm = user.applicationPermissions.sendPushNotification;

            if (perm == null || (boolean) perm) {
                lisPushEnabledInKWS();
            } else {
                lisPushDisabledInKWS();
            }

        } else {
            lisCheckError();
        }
    }

    @Override
    public void failure() {
        lisCheckError();
    }

    @Override
    public void execute(KWSServiceResponseInterface listener) {
        this.listener = (KWSCheckAllowedInterface) listener;
        super.execute(this.listener);
    }

    // <Private> functions

    private void lisPushEnabledInKWS () {
        if (listener != null) {
            listener.allowed(true, true);
        }
    }

    private void  lisPushDisabledInKWS () {
        if (listener != null) {
            listener.allowed(true, false);
        }
    }

    private void lisCheckError () {
        if (listener != null) {
            listener.allowed(false, false);
        }
    }
}

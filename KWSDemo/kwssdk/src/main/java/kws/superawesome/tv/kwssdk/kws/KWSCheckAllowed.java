package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.user.KWSUser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSCheckAllowed extends KWSService {

    // listener interface
    public KWSCheckAllowedInterface listener = null;

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
            try {
                JSONObject json = new JSONObject(payload);
                KWSUser user = new KWSUser(json);
                Object perm = user.applicationPermissions.sendPushNotification;

                if (perm == null || (boolean) perm) {
                    lisPushEnabledInKWS();
                } else {
                    lisPushDisabledInKWS();
                }

            } catch (JSONException e) {
                lisCheckError();
            }
        } else {
            lisCheckError();
        }
    }

    @Override
    public void failure() {
        lisCheckError();
    }

    // <Private> functions

    private void lisPushEnabledInKWS () {
        if (listener != null) {
            listener.pushAllowedInKWS();
        }
    }

    private void  lisPushDisabledInKWS () {
        if (listener != null) {
            listener.pushNotAllowedInKWS();
        }
    }

    private void lisCheckError () {
        if (listener != null) {
            listener.checkAllowedError();
        }
    }
}

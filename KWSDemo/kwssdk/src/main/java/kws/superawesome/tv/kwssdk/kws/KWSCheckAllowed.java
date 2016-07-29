package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sanetwork.request.*;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSCheckAllowed extends KWSRequest {

    // listener interface
    public KWSCheckAllowedInterface listener = null;

    @Override
    public String getEndpoint() {
        return "users/" + super.metadata.userId;
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.GET;
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

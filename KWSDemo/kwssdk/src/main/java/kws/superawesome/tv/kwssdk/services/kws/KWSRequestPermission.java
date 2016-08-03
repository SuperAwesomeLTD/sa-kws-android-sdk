package kws.superawesome.tv.kwssdk.services.kws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.error.KWSError;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSRequestPermission extends KWSService {

    // public listener
    private KWSRequestPermissionInterface listener = null;

    @Override
    public String getEndpoint() {
        return "users/" + super.metadata.userId + "/request-permissions";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[]{
                "permissions", SAJsonParser.newArray(new Object[]{
                    "sendPushNotification"
            })
        });
    }

    @Override
    public void success(int status, String payload) {
        Log.d("SuperAwesome", "Payload ==> " + payload);

        if (status == 200 || status == 204) {
            lisPushPermissionRequestedInKWS();
        }
        else {

            /** form the error - if exists */
            JSONObject json = SAJsonParser.newObject(payload);
            KWSError error = new KWSError(json);

            if (error.code == 5 && error.invalid != null && error.invalid.parentEmail != null && error.invalid.parentEmail.code == 6) {
                lisParentEmailIsMissingInKWS();
            }
            else {
                lisRequestError();
            }
        }
    }

    @Override
    public void failure() {
        lisRequestError();
    }

    @Override
    public void execute(KWSServiceResponseInterface listener) {
        this.listener = (KWSRequestPermissionInterface) listener;
        super.execute(this.listener);
    }

    // <Private> functions

    private void lisPushPermissionRequestedInKWS () {
        if (listener != null) {
            listener.requested(true, true);
        }
    }

    private void lisParentEmailIsMissingInKWS () {
        if (listener != null) {
            listener.requested(true, false);
        }
    }

    private void lisRequestError () {
        if (listener != null) {
            listener.requested(false, false);
        }
    }
}

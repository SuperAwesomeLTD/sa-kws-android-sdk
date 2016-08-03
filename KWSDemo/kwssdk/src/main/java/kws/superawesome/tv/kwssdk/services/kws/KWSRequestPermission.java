package kws.superawesome.tv.kwssdk.services.kws;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kws.superawesome.tv.kwssdk.models.error.KWSError;
import kws.superawesome.tv.kwssdk.models.user.KWSPermissions;
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

    // list of permissions
    private KWSPermissionType[] requestedPermissions = null;

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
        JSONArray array = new JSONArray();
        for (KWSPermissionType requestedPermission : requestedPermissions) {
            array.put(requestedPermission.toString());
        }
        return SAJsonParser.newObject(new Object[]{
                "permissions", array
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
    public void execute(Object param, KWSServiceResponseInterface listener) {

        this.listener = (KWSRequestPermissionInterface) listener;
        requestedPermissions = new KWSPermissionType[]{};

        if (param instanceof KWSPermissionType[]) {
            requestedPermissions = (KWSPermissionType[]) param;
        } else {
            lisRequestError();
            return;
        }

        Log.d("SuperAwesome", "Requesting KWS permission: " + Arrays.toString(requestedPermissions));
        super.execute(requestedPermissions, this.listener);
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

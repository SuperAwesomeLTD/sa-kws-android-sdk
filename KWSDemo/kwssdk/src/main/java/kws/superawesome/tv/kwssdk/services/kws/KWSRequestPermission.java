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

    private KWSRequestPermissionInterface listener = null;
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
    public void success(int status, String payload, boolean success) {
        Log.d("SuperAwesome", "Payload ==> " + payload);
        if (!success) {
            listener.requested(false, false);
        } else {
            if (status == 200 || status == 204) {
                listener.requested(true, true);
            } else {

                /** form the error - if exists */
                JSONObject json = SAJsonParser.newObject(payload);
                KWSError error = new KWSError(json);

                if (error.code == 5 && error.invalid != null && error.invalid.parentEmail != null && error.invalid.parentEmail.code == 6) {
                    listener.requested(true, false);
                } else {
                    listener.requested(false, false);
                }
            }
        }
    }

    @Override
    public void execute(Object param, KWSServiceResponseInterface listener) {
        KWSRequestPermissionInterface local = new KWSRequestPermissionInterface() {public void requested(boolean success, boolean requested) {}};
        this.listener = listener != null ? (KWSRequestPermissionInterface) listener : local;
        requestedPermissions = new KWSPermissionType[]{};

        if (param instanceof KWSPermissionType[]) {
            requestedPermissions = (KWSPermissionType[]) param;
        } else {
            this.listener.requested(false, false);
            return;
        }

        Log.d("SuperAwesome", "Requesting KWS permission: " + Arrays.toString(requestedPermissions));
        super.execute(requestedPermissions, this.listener);
    }
}

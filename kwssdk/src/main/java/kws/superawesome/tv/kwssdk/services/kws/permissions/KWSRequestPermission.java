package kws.superawesome.tv.kwssdk.services.kws.permissions;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import kws.superawesome.tv.kwssdk.models.error.KWSError;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSRequestPermission extends KWSService {

    private KWSChildrenRequestPermissionInterface listener = null;
    private KWSChildrenPermissionType[] requestedPermissions = null;

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.loggedUser.metadata.userId + "/request-permissions";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        JSONArray array = new JSONArray();
        for (KWSChildrenPermissionType requestedPermission : requestedPermissions) {
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
            listener.didRequestPermission(KWSChildrenRequestPermissionStatus.NetworkError);
        } else {
            if (status == 200 || status == 204) {
                listener.didRequestPermission(KWSChildrenRequestPermissionStatus.Success);
            } else {

                /** form the error - if exists */
                JSONObject json = SAJsonParser.newObject(payload);
                KWSError error = new KWSError(json);

                if (error.code == 10 && error.invalid != null && error.invalid.parentEmail != null && error.invalid.parentEmail.code == 6) {
                    listener.didRequestPermission(KWSChildrenRequestPermissionStatus.NoParentEmail);
                } else {
                    listener.didRequestPermission(KWSChildrenRequestPermissionStatus.NetworkError);
                }
            }
        }
    }

    @Override
    public void execute(Context context, Object param, KWSServiceResponseInterface listener) {
        KWSChildrenRequestPermissionInterface local = new KWSChildrenRequestPermissionInterface() {public void didRequestPermission(KWSChildrenRequestPermissionStatus status) {}};
        this.listener = listener != null ? (KWSChildrenRequestPermissionInterface) listener : local;
        requestedPermissions = new KWSChildrenPermissionType[]{};

        if (param instanceof KWSChildrenPermissionType[]) {
            requestedPermissions = (KWSChildrenPermissionType[]) param;
        } else {
            this.listener.didRequestPermission(KWSChildrenRequestPermissionStatus.NetworkError);
            return;
        }

        Log.d("SuperAwesome", "Requesting KWSChildren permission: " + Arrays.toString(requestedPermissions));
        super.execute(context, requestedPermissions, this.listener);
    }
}

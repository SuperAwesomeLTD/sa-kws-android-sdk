package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.user.KWSUser;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public class KWSGetUser extends KWSRequest {

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.metadata.userId;
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.GET;
    }

    @Override
    public void success(int status, String payload) {
        if ((status == 200 || status == 204) && payload != null) {
            try {
                KWSUser user = new KWSUser(new JSONObject(payload));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // failure
        }
    }

    @Override
    public void failure() {
        // do nothing
    }
}

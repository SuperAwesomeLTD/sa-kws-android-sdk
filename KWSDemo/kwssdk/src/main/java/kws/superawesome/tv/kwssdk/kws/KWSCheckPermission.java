package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwslib.SANetwork;
import kws.superawesome.tv.kwslib.SANetworkInterface;
import kws.superawesome.tv.kwslib.SANetworkResponse;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSUser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSCheckPermission {

    // listener interface
    public KWSCheckPermissionInterface listener = null;

    /**
     Main function of the class - that performs all the logic to find out if the
     user has or does not have push notification permissions
     */
    public void check () {

        if (KWS.sdk.kwsApiUrl != null && KWS.sdk.oauthToken != null && KWS.sdk.metadata != null){
            int userId= KWS.sdk.metadata.userId;
            String endpoint = KWS.sdk.kwsApiUrl + "users/" + userId;

            SANetwork network = new SANetwork();
            network.sendGET(endpoint, KWS.sdk.oauthToken, new SANetworkInterface() {
                @Override
                public void success(Object data) {
                    SANetworkResponse response = (SANetworkResponse)data;
                    int status = response.statusCode;

                    if (status == 200 || status == 204) {
                        try {
                            JSONObject json = new JSONObject(response.payload);
                            KWSUser user = new KWSUser(json);
                            Object perm = user.applicationPermissions.sendPushNotification;

                            if (perm == null || perm == true) {
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
            });
        } else {
            lisCheckError();
        }
    }

    // <Private> functions

    private void lisPushEnabledInKWS () {
        if (listener != null) {
            listener.pushEnabledInKWS();
        }
    }

    private void  lisPushDisabledInKWS () {
        if (listener != null) {
            listener.pushDisabledInKWS();
        }
    }

    private void lisCheckError () {
        if (listener != null) {
            listener.checkError();
        }
    }
}

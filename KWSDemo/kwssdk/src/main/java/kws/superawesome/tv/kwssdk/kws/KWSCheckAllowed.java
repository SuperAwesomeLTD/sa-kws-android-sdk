package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sanetwork.request.*;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import kws.superawesome.tv.kwssdk.models.KWSUser;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSCheckAllowed {

    // listener interface
    public KWSCheckAllowedInterface listener = null;

    /**
     Main function of the class - that performs all the logic to find out if the
     user has or does not have push notification permissions
     */
    public void check () {

        String kwsApiUrl = KWS.sdk.getKwsApiUrl();
        String oauthToken = KWS.sdk.getOauthToken();
        KWSMetadata metadata = KWS.sdk.getMetadata();

        if (kwsApiUrl != null && oauthToken != null && metadata != null){
            int userId= metadata.userId;
            String endpoint = kwsApiUrl + "users/" + userId;

            JSONObject header = new JSONObject();
            try {
                header.put("Authorization", "Bearer " + oauthToken);
                header.put("Content-Type", "application/json");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SANetwork network = new SANetwork();
            network.sendGET(SAApplication.getSAApplicationContext(), endpoint, new JSONObject(), header, new SANetworkInterface() {
                @Override
                public void success(int status, String payload) {
                    if (status == 200 || status == 204) {
                        try {
                            JSONObject json = new JSONObject(payload);
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
            listener.checkError();
        }
    }
}

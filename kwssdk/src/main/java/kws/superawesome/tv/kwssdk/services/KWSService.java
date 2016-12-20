package kws.superawesome.tv.kwssdk.services;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public class KWSService implements KWSServiceInterface {

    // protected request vars
    protected String kwsApiUrl;
    protected int appId;
    protected KWSLoggedUser loggedUser;
    protected String version;

    // private data
    private Context context = null;
    private SANetwork network;

    public KWSService() {
        network = new SANetwork();
    }

    @Override
    public String getEndpoint () {
        return null;
    }

    @Override
    public KWSHTTPMethod getMethod () {
        return KWSHTTPMethod.GET;
    }

    @Override
    public JSONObject getQuery () {
        return new JSONObject();
    }

    @Override
    public boolean needsLoggedUser () {
        return true;
    }

    @Override
    public JSONObject getHeader() {
        return SAJsonParser.newObject(new Object[]{
                "Authorization", "Bearer " + loggedUser.token,
                "Content-Type", "application/json",
                "kws-sdk-version", version
        });
    }

    @Override
    public JSONObject getBody() {
        return new JSONObject();
    }

    @Override
    public void success(int status, String payload, boolean success) {
        // do nothing
    }

    public void execute (Context context, KWSServiceResponseInterface listener) {
        kwsApiUrl = KWS.sdk.getKwsApiUrl();
        appId = KWS.sdk.getAppId();
        loggedUser = KWS.sdk.getLoggedUser();
        version = KWS.sdk.getVersion();
        this.context = context;

        // case when the Network request actually needs the SDK to have a logged user,
        // but there is no logged user whatsoever
        if (needsLoggedUser() && loggedUser == null) {
            success(0, null, false);
        }
        // case where either the user is logged or we don't need a logged user
        else {
            final KWSService instance = this;

            switch (getMethod()) {
                case GET: {
                    network.sendGET(context, kwsApiUrl + getEndpoint(), getQuery(), getHeader(), new SANetworkInterface() {
                        @Override
                        public void response(int status, String payload, boolean success) {
                            instance.success(status, payload, success);
                        }
                    });
                    break;
                }
                case POST: {
                    network.sendPOST(context, kwsApiUrl + getEndpoint(), getQuery(), getHeader(), getBody(), new SANetworkInterface() {
                        @Override
                        public void response(int status, String payload, boolean success) {
                            instance.success(status, payload, success);
                        }
                    });
                    break;
                }
                case PUT: {
                    network.sendPUT(context, kwsApiUrl + getEndpoint(), getQuery(), getHeader(), getBody(), new SANetworkInterface() {
                        @Override
                        public void response(int status, String payload, boolean success) {
                            instance.success(status, payload, success);
                        }
                    });
                    break;
                }
            }
        }
    }

    public void execute(Context context, Object param, KWSServiceResponseInterface listener) {
        execute(context, listener);
    }
}

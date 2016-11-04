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
    protected KWSLoggedUser loggedUser;
    protected String version;

    // private data
    private Context context = null;
    private SANetwork network;

    public KWSService() {
        network = new SANetwork();
    }

    @Override
    public String getEndpoint() {
        return null;
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public JSONObject getQuery() {
        return new JSONObject();
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
        loggedUser = KWS.sdk.getLoggedUser();
        version = KWS.sdk.getVersion();
        this.context = context;

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

    public void execute(Context context, Object param, KWSServiceResponseInterface listener) {
        execute(context, listener);
    }
}

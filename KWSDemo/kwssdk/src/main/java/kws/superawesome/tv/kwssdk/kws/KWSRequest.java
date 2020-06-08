package kws.superawesome.tv.kwssdk.kws;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.json.SAJsonParser;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import kws.superawesome.tv.kwssdk.network.SANetwork;
import kws.superawesome.tv.kwssdk.network.SANetworkInterface;

public class KWSRequest implements KWSRequestInterface {

    // protected request vars
    protected String kwsApiUrl;
    protected String oauthToken;
    protected String version;
    protected KWSMetadata metadata;

    // private data
    private SANetwork network;

    public KWSRequest () {
        network = new SANetwork();
    }

    @Override
    public String getEndpoint() {
        return null;
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.GET;
    }

    @Override
    public JSONObject getQuery() {
        return new JSONObject();
    }

    @Override
    public JSONObject getHeader() {
        return SAJsonParser.newObject(new Object[]{
                "Authorization", "Bearer " + oauthToken,
                "Content-Type", "application/json",
                "kws-sdk-version", version
        });
    }

    @Override
    public JSONObject getBody() {
        return new JSONObject();
    }

    @Override
    public boolean needsLoggedUser() {
        return true;
    }

    @Override
    public void success(int status, String payload) {
        // do nothing
    }

    @Override
    public void failure() {
        // do nothing
    }

    public void execute (Context c) {
        kwsApiUrl = KWS.sdk.getKwsApiUrl();
        oauthToken = KWS.sdk.getOauthToken();
        metadata = KWS.sdk.getMetadata();
        version = KWS.sdk.getVersion();

        final KWSRequest instance = this;

        // check to see if all is OK
        if ((kwsApiUrl == null || oauthToken == null || metadata == null) && needsLoggedUser()) {
            failure();
            return;
        }

        // continue!
        if (getMethod() == KWSRequestMethod.POST) {
            network.sendPOST(kwsApiUrl + getEndpoint(), getQuery(), getHeader(), getBody(), new SANetworkInterface() {
                @Override
                public void saDidGetResponse(int status, String payload, boolean success) {
                    if (success) {
                        instance.success(status, payload);
                    } else {
                        instance.failure();
                    }
                }
            });
        } else {
            network.sendGET(kwsApiUrl + getEndpoint(), getQuery(), getHeader(), new SANetworkInterface() {
                @Override
                public void saDidGetResponse(int status, String payload, boolean success) {
                    if (success) {
                        instance.success(status, payload);
                    } else {
                        instance.failure();
                    }
                }
            });
        }
    }

    public void execute(Context context, Object param) {
        execute(context);
    }
}

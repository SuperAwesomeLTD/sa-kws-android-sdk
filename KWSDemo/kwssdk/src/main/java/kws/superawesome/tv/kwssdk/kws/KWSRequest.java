package kws.superawesome.tv.kwssdk.kws;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public class KWSRequest implements KWSRequestInterface {

    // protected request vars
    protected String kwsApiUrl;
    protected String oauthToken;
    protected String version;
    protected KWSMetadata metadata;

    // private data
    private Context c;
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
    public void success(int status, String payload) {
        // do nothing
    }

    @Override
    public void failure() {
        // do nothing
    }

    public void execute () {
        kwsApiUrl = KWS.sdk.getKwsApiUrl();
        oauthToken = KWS.sdk.getOauthToken();
        metadata = KWS.sdk.getMetadata();
        version = KWS.sdk.getVersion();
        c = SAApplication.getSAApplicationContext();

        final KWSRequest instance = this;

        // check data
        if (kwsApiUrl != null && oauthToken != null && metadata != null) {
            if (getMethod() == KWSRequestMethod.POST) {
                network.sendPOST(c, kwsApiUrl + getEndpoint(), getQuery(), getHeader(), getBody(), new SANetworkInterface() {
                    @Override
                    public void response(int status, String payload, boolean success) {
                        if (success) {
                            instance.success(status, payload);
                        } else {
                            instance.failure();
                        }
                    }
                });
            } else {
                network.sendGET(c, kwsApiUrl + getEndpoint(), getQuery(), getHeader(), new SANetworkInterface() {
                    @Override
                    public void response(int status, String payload, boolean success) {
                        if (success) {
                            instance.success(status, payload);
                        } else {
                            instance.failure();
                        }
                    }
                });
            }
        }
        else {
            failure();
        }
    }

    public void execute(Object param) {
        execute();
    }
}

package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.appconfig.KWSAppConfig;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 04/01/2017.
 */

public class KWSGetAppConfig extends KWSRequest {

    private KWSGetAppConfigInterface listener = null;

    public KWSGetAppConfig () {
        listener = new KWSGetAppConfigInterface() {@Override public void gotAppConfig(KWSAppConfig config) {}};
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/config";
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.GET;
    }

    @Override
    public boolean needsLoggedUser() {
        return false;
    }

    @Override
    public JSONObject getHeader() {
        return new JSONObject();
    }

    @Override
    public JSONObject getQuery() {
        return SAJsonParser.newObject(new Object[] {
                "oauthClientId", KWS.sdk.getClientId()
        });
    }

    @Override
    public void success(int status, String payload) {
        if (status == 200 && payload != null) {
            KWSAppConfig config = new KWSAppConfig(payload);
            lisDidGetAppConfig(config);
        } else {
            lisDidGetAppConfig(null);
        }
    }

    public void execute (KWSGetAppConfigInterface listener) {
        this.listener = listener;
        super.execute();
    }

    private void lisDidGetAppConfig (KWSAppConfig config) {
        if (listener != null) {
            listener.gotAppConfig(config);
        }
    }
}

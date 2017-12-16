package kws.superawesome.tv.kwssdk.services.kws.randomname;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWSChildren;
import kws.superawesome.tv.kwssdk.models.appconfig.KWSAppConfig;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 04/01/2017.
 */

public class KWSGetAppConfig extends KWSService {

    private KWSGetAppConfigInterface listener = null;

    public KWSGetAppConfig () {
        listener = new KWSGetAppConfigInterface() {@Override public void gotAppConfig(KWSAppConfig config) {}};
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/config";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
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
                "oauthClientId", KWSChildren.sdk.getClientId()
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (success && status == 200 && payload != null) {
            KWSAppConfig config = new KWSAppConfig(payload);
            listener.gotAppConfig(config);
        } else {
            listener.gotAppConfig(null);
        }
    }

    @Override
    public void execute (Context context, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSGetAppConfigInterface) listener : this.listener;
        super.execute(context, this.listener);
    }
}

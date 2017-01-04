package kws.superawesome.tv.kwssdk.services.kws.randomname;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 20/12/2016.
 */

public class KWSRandomName extends KWSService {

    private int appId = 0;
    private KWSRandomNameInterface listener = null;

    public KWSRandomName () {
        listener = new KWSRandomNameInterface() {@Override public void gotRandomName(String name) {}};
    }

    @Override
    public String getEndpoint() {
        return "v2/apps/" + appId + "/random-display-name";
    }

    @Override
    public boolean needsLoggedUser() {
        return false;
    }

    @Override
    public JSONObject getHeader() {
        return SAJsonParser.newObject(new Object[]{});
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {

        if (!success) {
            listener.gotRandomName(null);
        } else {
            if (status == 200 && payload!= null) {
                listener.gotRandomName(payload.replace("\"", ""));
            } else {
                listener.gotRandomName(null);
            }
        }

    }

    public void execute(Context context, int appId, KWSServiceResponseInterface listener) {
        this.appId = appId;
        this.listener = listener != null ? (KWSRandomNameInterface) listener : this.listener;
        super.execute(context, this.listener);
    }

}

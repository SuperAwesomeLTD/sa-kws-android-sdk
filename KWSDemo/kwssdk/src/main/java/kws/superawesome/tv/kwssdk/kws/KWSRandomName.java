package kws.superawesome.tv.kwssdk.kws;

import android.content.Context;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 20/12/2016.
 */

public class KWSRandomName extends KWSRequest {

    private int appId = 0;
    private KWSRandomNameInterface listener = null;

    @Override
    public String getEndpoint() {
        return "v2/apps/" + appId + "/random-display-name";
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.GET;
    }

    @Override
    public JSONObject getHeader() {
        return SAJsonParser.newObject(new Object[]{});
    }

    @Override
    public boolean needsLoggedUser() {
        return false;
    }

    @Override
    public void success(int status, String payload) {
        if (status == 200 && payload != null) {
            lisDidGetRandomName(payload.replace("\"", ""));
        } else {
            lisDidGetRandomName(null);
        }
    }

    @Override
    public void failure() {
        lisDidGetRandomName(null);
    }

    public void execute (Context context, int appId, KWSRandomNameInterface listener) {
        this.listener = listener;
        this.appId = appId;
        super.execute(context);
    }

    private void lisDidGetRandomName (String name) {
        if (listener != null) {
            listener.didGetRandomName(name);
        }
    }
}

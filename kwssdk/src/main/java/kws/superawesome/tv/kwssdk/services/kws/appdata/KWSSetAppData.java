package kws.superawesome.tv.kwssdk.services.kws.appdata;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 25/08/16.
 */
public class KWSSetAppData extends KWSService {

    private KWSChildrenSetAppDataInterface listener = null;
    private String name = null;
    private int value = 0;

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.loggedUser.metadata.appId + "/users/" + super.loggedUser.metadata.userId + "/app-data/set";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[] {
                "name", name,
                "value", value
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        listener.didSetAppData(success && (status == 200 || status == 204));
    }

    public void execute(Context context, String name, int value, KWSServiceResponseInterface listener) {
        this.name = name;
        this.value = value;
        this.listener = listener != null? (KWSChildrenSetAppDataInterface) listener : new KWSChildrenSetAppDataInterface() { @Override public void didSetAppData(boolean success) {}};
        super.execute(context, listener);
    }
}

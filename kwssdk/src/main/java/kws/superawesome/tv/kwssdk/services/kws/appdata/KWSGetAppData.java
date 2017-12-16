package kws.superawesome.tv.kwssdk.services.kws.appdata;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kws.superawesome.tv.kwssdk.models.appdata.KWSAppData;
import kws.superawesome.tv.kwssdk.models.appdata.KWSAppDataResponse;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 25/08/16.
 */
public class KWSGetAppData extends KWSService {

    private KWSChildrenGetAppDataInterface listener = null;

    public KWSGetAppData () {
        listener = new KWSChildrenGetAppDataInterface() { @Override public void didGetAppData(List<KWSAppData> appData) {} };
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.loggedUser.metadata.appId + "/users/" + super.loggedUser.metadata.userId + "/app-data";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.didGetAppData(new ArrayList<KWSAppData>());
        } else {
            if (status == 200) {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSAppDataResponse response = new KWSAppDataResponse(jsonObject);
                listener.didGetAppData(response.results);
            } else {
                listener.didGetAppData(new ArrayList<KWSAppData>());
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSChildrenGetAppDataInterface)listener : this.listener;
        super.execute(context, listener);
    }
}

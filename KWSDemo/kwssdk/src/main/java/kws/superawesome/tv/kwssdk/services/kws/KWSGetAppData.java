package kws.superawesome.tv.kwssdk.services.kws;

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

    private KWSGetAppDataInterface listener = null;

    @Override
    public String getEndpoint() {
        return "apps/" + super.metadata.appId + "/users/" + super.metadata.userId + "/app-data";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.gotAppData(new ArrayList<KWSAppData>());
        } else {
            if (status == 200) {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSAppDataResponse response = new KWSAppDataResponse(jsonObject);
                listener.gotAppData(response.results);
            } else {
                listener.gotAppData(new ArrayList<KWSAppData>());
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSGetAppDataInterface)listener : new KWSGetAppDataInterface() {@Override public void gotAppData(List<KWSAppData> appData) {}};
        super.execute(context, listener);
    }
}

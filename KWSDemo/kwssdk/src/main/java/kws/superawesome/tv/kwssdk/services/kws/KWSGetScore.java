package kws.superawesome.tv.kwssdk.services.kws;

import android.util.Log;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.user.KWSScore;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public class KWSGetScore extends KWSService {

    private KWSGetScoreInterface listener = null;

    @Override
    public String getEndpoint() {
        return "apps/" + super.metadata.appId + "/score";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.gotScore(null);
        } else {
            if (status == 200 && payload != null) {
                Log.d("SuperAwesome", "Payload ==> " + payload);
                JSONObject json = SAJsonParser.newObject(payload);
                KWSScore score = new KWSScore(json);
                listener.gotScore(score);
            } else {
                listener.gotScore(null);
            }
        }
    }

    @Override
    public void execute(KWSServiceResponseInterface listener) {
        KWSGetScoreInterface local = new KWSGetScoreInterface() {@Override public void gotScore(KWSScore score) {}};
        this.listener = listener != null ? (KWSGetScoreInterface) listener : local;
        super.execute(this.listener);
    }
}

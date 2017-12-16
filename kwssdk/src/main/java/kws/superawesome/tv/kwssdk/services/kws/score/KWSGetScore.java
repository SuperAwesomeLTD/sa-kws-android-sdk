package kws.superawesome.tv.kwssdk.services.kws.score;

import android.content.Context;
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

    private KWSChildrenGetScoreInterface listener = null;

    public KWSGetScore () {
        listener = new KWSChildrenGetScoreInterface() { @Override public void didGetScore(KWSScore score) {} };
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.loggedUser.metadata.appId + "/score";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.didGetScore(null);
        } else {
            if (status == 200 && payload != null) {
                Log.d("SuperAwesome", "Payload ==> " + payload);
                JSONObject json = SAJsonParser.newObject(payload);
                KWSScore score = new KWSScore(json);
                listener.didGetScore(score);
            } else {
                listener.didGetScore(null);
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSChildrenGetScoreInterface) listener : this.listener;
        super.execute(context, this.listener);
    }
}

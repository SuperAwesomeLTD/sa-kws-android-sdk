package kws.superawesome.tv.kwssdk.services.kws;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeaderboard;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public class KWSGetLeaderboard extends KWSService {

    private KWSGetLeaderboardInterface listener = null;

    @Override
    public String getEndpoint() {
        return "apps/" + super.metadata.appId + "/leaders";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.gotLeaderboard(new ArrayList<KWSLeader>());
        } else {
            if ((status == 200 || status == 204) && payload != null) {
                Log.d("SuperAwesome", "Payload ==> " + payload);
                JSONObject json = SAJsonParser.newObject(payload);
                KWSLeaderboard leaderboard = new KWSLeaderboard(json);
                listener.gotLeaderboard(leaderboard.results);
            } else {
                listener.gotLeaderboard(new ArrayList<KWSLeader>());
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        KWSGetLeaderboardInterface local = new KWSGetLeaderboardInterface() {public void gotLeaderboard(List<KWSLeader> leaderboard) {}};
        this.listener = listener != null ? (KWSGetLeaderboardInterface) listener : local;
        super.execute(context, this.listener);
    }
}

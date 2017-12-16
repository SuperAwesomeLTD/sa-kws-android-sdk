package kws.superawesome.tv.kwssdk.services.kws.score;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    private KWSChildrenGetLeaderboardInterface listener = null;

    public KWSGetLeaderboard () {
        listener = new KWSChildrenGetLeaderboardInterface() { @Override public void didGetLeaderboard(List<KWSLeader> leaderboard) {} };
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.loggedUser.metadata.appId + "/leaders";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.didGetLeaderboard(new ArrayList<KWSLeader>());
        } else {
            if ((status == 200 || status == 204) && payload != null) {
                JSONObject json = SAJsonParser.newObject(payload);
                KWSLeaderboard leaderboard = new KWSLeaderboard(json);
                listener.didGetLeaderboard(leaderboard.results);
            } else {
                listener.didGetLeaderboard(new ArrayList<KWSLeader>());
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSChildrenGetLeaderboardInterface) listener : this.listener;
        super.execute(context, this.listener);
    }
}

package kws.superawesome.tv.kwssdk.services.kws;

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

    // listener
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
    public void success(int status, String payload) {
        if ((status == 200 || status == 204) && payload != null) {
            Log.d("SuperAwesome", "Payload ==> " + payload);
            JSONObject json = SAJsonParser.newObject(payload);
            KWSLeaderboard leaderboard = new KWSLeaderboard(json);
            lisGotLeaderboardOK(leaderboard.results);
        } else {
            ligGotLeaderboardNOK();
        }
    }

    @Override
    public void failure() {
        // do nothing
    }

    @Override
    public void execute(KWSServiceResponseInterface listener) {
        this.listener = (KWSGetLeaderboardInterface) listener;
        super.execute(this.listener);
    }

    // list functions
    private void lisGotLeaderboardOK (List<KWSLeader> leaderboard) {
        if (listener != null) {
            listener.gotLeaderboard(leaderboard);
        }
    }

    private void ligGotLeaderboardNOK () {
        if (listener != null) {
            listener.gotLeaderboard(new ArrayList<KWSLeader>());
        }
    }
}

package kws.superawesome.tv.kwssdk.kws;

import android.util.Log;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public class KWSGetLeaderboard extends KWSService {

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
        Log.d("SuperAwesome", status + " " + payload);
    }

    @Override
    public void failure() {
        // do nothing
    }
}

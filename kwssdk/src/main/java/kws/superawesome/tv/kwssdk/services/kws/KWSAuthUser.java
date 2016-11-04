package kws.superawesome.tv.kwssdk.services.kws;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 12/10/16.
 */
public class KWSAuthUser extends KWSService {

    private KWSLoggedUser loggedUser;
    private KWSAuthUserInterface listener;
    private Context context;

    public KWSAuthUser () {
        listener = new KWSAuthUserInterface() { @Override public void authUser(int status, KWSLoggedUser loggedUser) {} };
    }

    @Override
    public String getEndpoint() {
        return "oauth/authorise?access_token=" + loggedUser.accessToken;
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getHeader() {
        return SAJsonParser.newObject(new Object[] {
                "Content-Type", "application/json"
        });
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[] {
                "response_type", "token",
                "client_id", KWS.sdk.getClientId(),
                "redirect_uri", (context != null ? context.getPackageName() : "com.test.app") + ":/"
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.authUser(status, null);
        } else {
            if (status == 200 && payload != null) {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSLoggedUser loggedUser = new KWSLoggedUser(jsonObject);
                listener.authUser(status, loggedUser.token != null ? loggedUser : null);
            } else {
                listener.authUser(status, null);
            }
        }
    }

    public void execute (Context context, KWSLoggedUser loggedUser, KWSAuthUserInterface listener) {
        this.context = context;
        this.loggedUser = loggedUser;
        this.listener = listener != null ? listener : this.listener;
        super.execute(context, (KWSServiceResponseInterface) listener);
    }
}

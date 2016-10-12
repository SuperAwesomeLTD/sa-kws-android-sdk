package kws.superawesome.tv.kwssdk.services.kws;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.user.KWSUserCreateDetail;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;

/**
 * Created by gabriel.coman on 08/10/16.
 */
public class KWSCreateUser extends KWSService {

    private KWSCreateUserInterface listener = null;
    private KWSLoggedUser loggedUser;
    private String password;

    public KWSCreateUser () {
        listener = new KWSCreateUserInterface() { @Override public void created(int status, KWSLoggedUser loggedUser) {}};
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/" + loggedUser.metadata.appId + "/users?access_token=" + loggedUser.accessToken;
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
                "username", loggedUser.username,
                "password", password,
                "dateOfBirth", loggedUser.dateOfBirth,
                "country", loggedUser.country,
                "parentEmail", loggedUser.parentEmail,
                "authenticate", true
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {

        if (!success) {
            listener.created(status, null);
        } else {
            if (status == 201 && payload != null) {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSLoggedUser loggedUser = new KWSLoggedUser(jsonObject);
                listener.created(status, loggedUser.token != null ? loggedUser : null);
            } else {
                listener.created(status, null);
            }
        }
    }

    public void execute (Context context, KWSLoggedUser loggedUser, String password, KWSCreateUserInterface listener) {
        this.listener = listener != null ? listener : this.listener;
        this.loggedUser = loggedUser;
        this.password = password;
        super.execute(context, this.listener);
    }
}

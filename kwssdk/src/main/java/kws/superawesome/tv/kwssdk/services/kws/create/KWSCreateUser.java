package kws.superawesome.tv.kwssdk.services.kws.create;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 08/10/16.
 */
public class KWSCreateUser extends KWSService {

    private KWSCreateUserInterface listener = null;

    private int appId;
    private String token;
    private String username;
    private String password;
    private String parentEmail;
    private String country;
    private String dateOfBirth;

    public KWSCreateUser () {
        listener = new KWSCreateUserInterface() { @Override public void created(int status, KWSLoggedUser loggedUser) {}};
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/" + appId + "/users?access_token=" + token;
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public boolean needsLoggedUser() {
        return false;
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
                "username", username,
                "password", password,
                "dateOfBirth", dateOfBirth,
                "country", country,
                "parentEmail", parentEmail,
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

    public void execute (Context context,
                         String token,
                         int appId,
                         String username,
                         String password,
                         String dateOfBirth,
                         String country,
                         String parentEmail,
                         KWSCreateUserInterface listener) {
        this.appId = appId;
        this.token = token;
        this.username = username;
        this.password = password;
        this.parentEmail = parentEmail;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.listener = listener != null ? listener : this.listener;
        super.execute(context, this.listener);
    }
}

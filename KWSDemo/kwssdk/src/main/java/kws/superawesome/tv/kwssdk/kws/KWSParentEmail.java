package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sanetwork.request.*;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSParentEmail extends KWSRequest {

    // public listener
    public KWSParentEmailInterface listener = null;

    // private var
    private String emailToSubmit = null;

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.metadata.userId + "/request-permissions";
    }

    @Override
    public KWSRequestMethod getMethod() {
        return KWSRequestMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[]{
                "parentEmail", emailToSubmit,
                "permissions", SAJsonParser.newArray(new Object[]{
                    "sendPushNotification"
            })
        });
    }

    @Override
    public void success(int status, String payload) {
        if (status == 200 || status == 204) {
            lisEmailSubmittedInKWS();
        }
        else {
            lisEmailError();
        }
    }

    @Override
    public void failure() {
        lisEmailError();
    }

    @Override
    public void execute(Object param) {

        // get param and correct type
        if (param instanceof String) {
            emailToSubmit = (String)param;
        }  else {
            lisInvalidError();
            return;
        }

        // check params
        if (emailToSubmit.length() == 0 || !SAUtils.isValidEmail(emailToSubmit)) {
            lisInvalidError();
            return;
        }

        super.execute(param);
    }

    // <Private> functions

    void lisEmailSubmittedInKWS () {
        if (listener != null) {
            listener.emailSubmittedInKWS();
        }
    }

    void lisEmailError () {
        if (listener != null) {
            listener.emailError();
        }
    }

    void lisInvalidError () {
        if (listener != null) {
            listener.invalidEmail();
        }
    }
}

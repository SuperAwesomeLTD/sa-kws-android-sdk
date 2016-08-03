package kws.superawesome.tv.kwssdk.services.kws;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSParentEmail extends KWSService {

    // public listener
    private KWSParentEmailInterface listener = null;

    // private var
    private String emailToSubmit = null;

    @Override
    public String getEndpoint() {
        return "users/" + super.metadata.userId + "/request-permissions";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
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
    public void execute(Object param, KWSServiceResponseInterface listener) {

        this.listener = (KWSParentEmailInterface) listener;

        // get param and correct type
        if (param instanceof String) {
            emailToSubmit = (String)param;
        }  else {
            lisEmailError();
            return;
        }

        // check params
        if (emailToSubmit.length() == 0 || !SAUtils.isValidEmail(emailToSubmit)) {
            lisEmailError();
            return;
        }

        super.execute(emailToSubmit, this.listener);
    }

    // <Private> functions

    void lisEmailSubmittedInKWS () {
        if (listener != null) {
            listener.submitted(true);
        }
    }

    void lisEmailError () {
        if (listener != null) {
            listener.submitted(false);
        }
    }
}

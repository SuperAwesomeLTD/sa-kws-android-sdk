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

    private KWSParentEmailInterface listener = null;
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
    public void success(int status, String payload, boolean success) {
        listener.submitted (success && (status == 200 || status == 204));
    }

    @Override
    public void execute(Object param, KWSServiceResponseInterface listener) {
        KWSParentEmailInterface local = new KWSParentEmailInterface() {public void submitted(boolean success) {}};
        this.listener = listener != null ? (KWSParentEmailInterface) listener : local;

        // get param and correct type
        if (param instanceof String) {
            emailToSubmit = (String)param;
        }  else {
            this.listener.submitted(false);
            return;
        }

        // check params
        if (emailToSubmit.length() == 0 || !SAUtils.isValidEmail(emailToSubmit)) {
            this.listener.submitted(false);
            return;
        }

        super.execute(emailToSubmit, this.listener);
    }
}

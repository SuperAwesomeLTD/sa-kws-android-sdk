package kws.superawesome.tv.kwssdk.services.kws.parentemail;

import android.content.Context;

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

    private KWSChildrenUpdateParentEmailInterface listener = null;
    private String emailToSubmit = null;

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.loggedUser.metadata.userId + "/request-permissions";
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
                    "SendPushNotification"
            })
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.NetworkError);
        } else {
            if (status == 200 || status == 204) {
                listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.Success);
            } else {
                listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.NetworkError);
            }
        }
    }

    @Override
    public void execute(Context context, Object param, KWSServiceResponseInterface listener) {
        KWSChildrenUpdateParentEmailInterface local = new KWSChildrenUpdateParentEmailInterface() {public void didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus status) {}};
        this.listener = listener != null ? (KWSChildrenUpdateParentEmailInterface) listener : local;

        // getService param and correct type
        if (param instanceof String) {
            emailToSubmit = (String)param;
        }  else {
            this.listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.InvalidEmail);
            return;
        }

        // check params
        if (emailToSubmit.length() == 0 || !SAUtils.isValidEmail(emailToSubmit)) {
            this.listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.InvalidEmail);
            return;
        }

        super.execute(context, emailToSubmit, this.listener);
    }
}

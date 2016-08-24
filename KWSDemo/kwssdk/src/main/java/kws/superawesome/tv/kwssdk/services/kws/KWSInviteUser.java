package kws.superawesome.tv.kwssdk.services.kws;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public class KWSInviteUser extends KWSService {

    private String emailAddress = null;
    private KWSInviteUserInterface listener = null;

    @Override
    public String getEndpoint() {
        return "users/" + super.metadata.userId + "/invite-user";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[] {
                "emailAddress", emailAddress
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.invited(false);
        } else {
            if (status == 200 || status == 204) {
                listener.invited(true);
            } else {
                listener.invited(false);
            }
        }
    }

    public void execute(String emailAddress, KWSServiceResponseInterface listener) {
        // get vars
        this.emailAddress = emailAddress;

        // get listener
        this.listener = listener != null ? (KWSInviteUserInterface) listener : new KWSInviteUserInterface() { @Override public void invited(boolean success) {}};

        // check params
        if (emailAddress.length() == 0 || !SAUtils.isValidEmail(emailAddress)) {
            this.listener.invited(false);
            return;
        }

        // execute
        super.execute(this.listener);
    }
}

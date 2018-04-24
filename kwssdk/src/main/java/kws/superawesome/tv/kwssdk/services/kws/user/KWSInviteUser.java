package kws.superawesome.tv.kwssdk.services.kws.user;

import android.content.Context;

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
    private KWSChildrenInviteUserInterface listener = null;

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.loggedUser.metadata.userId + "/invite-user";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[] {
                "email", emailAddress
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        listener.didInviteUser(success && (status == 200 || status == 204));
    }

    public void execute(Context context, String emailAddress, KWSServiceResponseInterface listener) {
        // getService vars
        this.emailAddress = emailAddress;

        // getService listener
        this.listener = listener != null ? (KWSChildrenInviteUserInterface) listener : new KWSChildrenInviteUserInterface() { @Override public void didInviteUser(boolean success) {}};

        // check params
        if (emailAddress.length() == 0 || !SAUtils.isValidEmail(emailAddress)) {
            this.listener.didInviteUser(false);
            return;
        }

        // execute
        super.execute(context, this.listener);
    }
}

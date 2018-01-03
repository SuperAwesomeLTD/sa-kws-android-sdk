package kws.superawesome.tv.kwssdk.services.kws.user;

import android.content.Context;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.error.KWSError;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 25/08/16.
 */
public class KWSUpdateUser extends KWSService {

    private KWSUser updatedUser = null;
    private KWSChildrenUpdateUserInterface listener = null;

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.loggedUser.metadata.clientId;
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.PUT;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[] {
                "firstName", updatedUser.firstName,
                "lastName", updatedUser.lastName,
                "dateOfBirth", updatedUser.dateOfBirth,
                "email", updatedUser.email,
                "phoneNumber", updatedUser.phoneNumber,
                "gender", updatedUser.gender,
                "language", updatedUser.language,
                "addressResponse", SAJsonParser.newObject(new Object[] {
                        "street", updatedUser.address.street,
                        "postCode", updatedUser.address.postCode,
                        "city", updatedUser.address.city,
                        "country", updatedUser.address.country
                }),
                "applicationProfileResponse", SAJsonParser.newObject(new Object[] {
                        "avatarId", updatedUser.applicationProfile.avatarId,
                        "customField1", updatedUser.applicationProfile.customField1,
                        "customField2", updatedUser.applicationProfile.customField2,
                        "customField3", updatedUser.applicationProfile.customField3,
                        "customField4", updatedUser.applicationProfile.customField4,
                        "customField5", updatedUser.applicationProfile.customField5,
                })
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.didUpdateUser(false);
        } else {
            if (status == 200 || status == 204) {
                listener.didUpdateUser(true);
            } else {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSError error = new KWSError(jsonObject);

                // forbidden code
                if (error.code == 1 || error.code == 5) {
                    listener.didUpdateUser(false);
                }
            }
        }
    }

    public void execute(Context context, KWSUser user, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSChildrenUpdateUserInterface) listener : new KWSChildrenUpdateUserInterface() {@Override public void didUpdateUser(boolean updated) {}};
        updatedUser = user;
        super.execute(context, listener);
    }
}

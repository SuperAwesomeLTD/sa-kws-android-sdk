package kws.superawesome.tv.kwssdk.services.kws;

import android.content.Context;

import org.json.JSONObject;

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
    private String username = null;
    private String password = null;
    private String dateOfBirth = null;
    private String country = null;
    private SANetwork network = null;

    public KWSCreateUser () {
        listener = new KWSCreateUserInterface() { @Override public void created(boolean success, String token) {}};
        network = new SANetwork();
    }

    @Override
    public String getEndpoint() {
        return "https://kwsdemobackend.herokuapp.com/create";
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
                "username", username,
                "password", password,
                "dateOfBirth", dateOfBirth,
                "country", country
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.created(false, null);
        } else {
            if (status == 200 && payload != null) {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSUserCreateDetail user = new KWSUserCreateDetail(jsonObject);
                if (user.token != null) {
                    listener.created(true, user.token);
                } else {
                    listener.created(false, null);
                }
            } else {
                listener.created(false, null);
            }
        }
    }

    public void execute (Context context, String username, String password, String dateOfBirth, String country, KWSCreateUserInterface listener) {
        this.listener = listener != null ? listener : this.listener;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.country = country;

        final KWSCreateUser instance = this;

        // super.execute ();
        network.sendPOST(context, getEndpoint(), getQuery(), getHeader(), getBody(), new SANetworkInterface() {
            @Override
            public void response(int status, String payload, boolean success) {
                instance.success(status, payload, success);
            }
        });

    }
}

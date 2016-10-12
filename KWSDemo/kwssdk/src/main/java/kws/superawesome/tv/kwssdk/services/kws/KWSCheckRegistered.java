package kws.superawesome.tv.kwssdk.services.kws;

import android.content.Context;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 14/07/16.
 */
public class KWSCheckRegistered extends KWSService {

    private KWSCheckRegisteredInterface listener = null;

    public KWSCheckRegistered () {
        listener = new KWSCheckRegisteredInterface() { @Override public void allowed(boolean registered) {} };
    }

    @Override
    public String getEndpoint() {
        return "v1/apps/" + super.loggedUser.metadata.appId + "/users/" + super.loggedUser.metadata.userId + "/has-device-token";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.allowed(false);
        } else {
            if (payload.contentEquals("true")) {
                listener.allowed(true);
            } else if (payload.contentEquals("false")) {
                listener.allowed(false);
            } else {
                listener.allowed(false);
            }
        }
    }

    @Override
    public void execute(Context context, KWSServiceResponseInterface listener) {
        this.listener = listener != null ? (KWSCheckRegisteredInterface) listener : this.listener;
        super.execute(context, this.listener);
    }
}

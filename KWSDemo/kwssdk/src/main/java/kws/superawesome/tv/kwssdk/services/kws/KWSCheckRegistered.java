package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 14/07/16.
 */
public class KWSCheckRegistered extends KWSService {

    private KWSCheckRegisteredInterface listener = null;

    @Override
    public String getEndpoint() {
        return "apps/" + super.metadata.appId + "/users/" + super.metadata.userId + "/has-device-token";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.GET;
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.allowed(false, false);
        } else {
            if (payload.contentEquals("true")) {
                listener.allowed(true, true);
            } else if (payload.contentEquals("false")) {
                listener.allowed(true, false);
            } else {
                listener.allowed(false, false);
            }
        }
    }

    @Override
    public void execute(KWSServiceResponseInterface listener) {
        KWSCheckRegisteredInterface local = new KWSCheckRegisteredInterface() {public void allowed(boolean success, boolean registered) {}};
        this.listener = listener != null ? (KWSCheckRegisteredInterface) listener : local;
        super.execute(this.listener);
    }
}

package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 14/07/16.
 */
public class KWSCheckRegistered extends KWSService {

    // listener
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
    public void success(int status, String payload) {
        if (payload.contentEquals("true")) {
            lisUserIsRegistered();
        } else if (payload.contentEquals("false")) {
            lisUserIsNotRegistered();
        } else {
            lisCheckRegisteredError();
        }
    }

    @Override
    public void failure() {
        lisCheckRegisteredError();
    }

    @Override
    public void execute(KWSServiceResponseInterface listener) {
        this.listener = (KWSCheckRegisteredInterface) listener;
        super.execute(this.listener);
    }

    // Listener functions

    void lisUserIsRegistered () {
        if (listener != null) {
            listener.allowed(true, true);
        }
    }

    void lisUserIsNotRegistered () {
        if (listener != null) {
            listener.allowed(true, false);
        }
    }

    void lisCheckRegisteredError () {
        if (listener != null) {
            listener.allowed(false, false);
        }
    }
}

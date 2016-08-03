package kws.superawesome.tv.kwssdk.managers;

import kws.superawesome.tv.kwssdk.kws.KWSCheckAllowed;
import kws.superawesome.tv.kwssdk.kws.KWSCheckAllowedInterface;
import kws.superawesome.tv.kwssdk.kws.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.kws.KWSRequestPermissionInterface;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class KWSManager implements KWSCheckAllowedInterface, KWSRequestPermissionInterface {

    // listener
    public KWSManagerInterface listener = null;

    // private vars
    private KWSCheckAllowed kwsCheck = null;
    private KWSRequestPermission kwsRequest = null;

    // private constructor
    public KWSManager(){
        kwsCheck = new KWSCheckAllowed();
        kwsRequest = new KWSRequestPermission();
    }

    // main func
    public void checkIfNotficationsAreAllowed () {
        kwsCheck.listener = this;
        kwsCheck.execute();
    }

    // <KWSCheckPermissionProtocol>

    @Override
    public void pushAllowedInKWS() {
        kwsRequest.listener = this;
        kwsRequest.execute();
    }

    @Override
    public void pushNotAllowedInKWS() {
        lisPushDisabledInKWS();
    }

    @Override
    public void checkAllowedError() {
        lisNetworkErrorCheckingForKWS();
    }

    // <KWSRequestPermissionProtocol>

    @Override
    public void pushPermissionRequestedInKWS() {
        lisIsAllowedToRegister();
    }

    @Override
    public void parentEmailIsMissingInKWS() {
        lisParentEmailIsMissingInKWS();
    }

    @Override
    public void permissionError() {
        lisNetworkErrorRequestingPermissionFromKWS();
    }

    // <Private Del> functions

    void lisPushDisabledInKWS () {
        if (listener != null) {
            listener.pushDisabledInKWS();
        }
    }

    void lisParentEmailIsMissingInKWS () {
        if (listener != null) {
            listener.parentEmailIsMissingInKWS();
        }
    }

    void lisNetworkErrorCheckingForKWS () {
        if (listener != null) {
            listener.networkErrorCheckingForKWS();
        }
    }

    void lisNetworkErrorRequestingPermissionFromKWS () {
        if (listener != null) {
            listener.networkErrorRequestingPermissionFromKWS();
        }
    }

    void lisIsAllowedToRegister () {
        if (listener != null) {
            listener.isAllowedToRegister();
        }
    }
}

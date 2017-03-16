package kws.superawesome.tv.kwssdk.managers;

import android.content.Context;

import kws.superawesome.tv.kwssdk.kws.KWSCheckAllowed;
import kws.superawesome.tv.kwssdk.kws.KWSCheckAllowedInterface;
import kws.superawesome.tv.kwssdk.kws.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.kws.KWSRequestPermissionInterface;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class KWSManager implements KWSCheckAllowedInterface, KWSRequestPermissionInterface {

    private Context context = null;

    // singleton instance
    public static KWSManager sharedInstance = new KWSManager();

    // listener
    public KWSManagerInterface listener = null;

    // private vars
    private KWSCheckAllowed kwsCheck = null;
    private KWSRequestPermission kwsRequest = null;

    // private constructor
    private KWSManager(){
        kwsCheck = new KWSCheckAllowed();
        kwsRequest = new KWSRequestPermission();
    }

    // main func
    public void checkIfNotficationsAreAllowed (Context context) {
        kwsCheck.listener = this;
        this.context = context;
        kwsCheck.execute(context);
    }

    // <KWSCheckPermissionProtocol>

    @Override
    public void pushAllowedInKWS() {
        kwsRequest.listener = this;
        kwsRequest.execute(context);
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

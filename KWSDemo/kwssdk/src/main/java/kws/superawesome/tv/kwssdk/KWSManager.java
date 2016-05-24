package kws.superawesome.tv.kwssdk;

import android.view.View;

import kws.superawesome.tv.kwssdk.kws.KWSCheckPermission;
import kws.superawesome.tv.kwssdk.kws.KWSCheckPermissionInterface;
import kws.superawesome.tv.kwssdk.kws.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.kws.KWSRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.push.PushRegisterPermission;
import kws.superawesome.tv.kwssdk.push.PushRegisterPermissionInterface;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class KWSManager implements KWSCheckPermissionInterface, KWSRequestPermissionInterface, PushRegisterPermissionInterface {

    // singleton instance
    public static KWSManager sharedInstance = new KWSManager();

    // listener
    public KWSManagerInterface listener = null;

    // private vars
    private PushRegisterPermission pushRegister = null;
    private KWSCheckPermission kwsCheck = null;
    private KWSRequestPermission kwsRequest = null;

    // private constructor
    private KWSManager(){
        pushRegister = new PushRegisterPermission();
        kwsCheck = new KWSCheckPermission();
        kwsRequest = new KWSRequestPermission();
    }

    // main func
    public void checkIfNotficationsAreAllowed () {
        kwsCheck.listener = this;
        kwsCheck.check();
    }

    // <KWSCheckPermissionProtocol>

    @Override
    public void pushEnabledInKWS() {
        pushRegister.listener = this;
        pushRegister.isRegistered();
    }

    @Override
    public void pushDisabledInKWS() {
        pushRegister.unregister();
        lisPushDisabledInKWS();
    }

    @Override
    public void checkError() {
        lisNetworkError();
    }

    // <PushRegisterPermissionProtocol>

    @Override
    public void isRegisteredInSystem() {
        lisIsAlreadyRegistered();
    }

    @Override
    public void isNotRegisteredInSystem() {
        kwsRequest.listener = this;
        kwsRequest.request();
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
    public void requestError() {
        lisNetworkError();
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

    void lisNetworkError () {
        if (listener != null) {
            listener.networkError();
        }
    }

    void lisIsAllowedToRegister () {
        if (listener != null) {
            listener.isAllowedToRegister();
        }
    }

    void lisIsAlreadyRegistered () {
        if (listener != null) {
            listener.isAlreadyRegistered();
        }
    }
}

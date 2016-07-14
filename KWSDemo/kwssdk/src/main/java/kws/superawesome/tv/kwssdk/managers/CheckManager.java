package kws.superawesome.tv.kwssdk.managers;

import kws.superawesome.tv.kwssdk.kws.KWSCheckAllowed;
import kws.superawesome.tv.kwssdk.kws.KWSCheckAllowedInterface;
import kws.superawesome.tv.kwssdk.kws.KWSCheckRegistered;
import kws.superawesome.tv.kwssdk.kws.KWSCheckRegisteredInterface;

/**
 * Created by gabriel.coman on 14/07/16.
 */
public class CheckManager implements KWSCheckAllowedInterface, KWSCheckRegisteredInterface {

    // singleton instance
    public static CheckManager sharedInstance = new CheckManager();

    // listener
    public CheckManagerInterface listener = null;

    // private vars
    private KWSCheckAllowed kwsCheckAllowed = null;
    private KWSCheckRegistered kwsCheckRegistered = null;

    // private constructor
    private CheckManager(){
        kwsCheckAllowed = new KWSCheckAllowed();
        kwsCheckAllowed.listener = this;
        kwsCheckRegistered = new KWSCheckRegistered();
        kwsCheckRegistered.listener = this;
    }

    // main func
    public void areNotificationsEnabled () {
        kwsCheckAllowed.check();
    }

    // KWSCheckAllowedInterface

    @Override
    public void pushAllowedInKWS() {
        kwsCheckRegistered.check();
    }

    @Override
    public void pushNotAllowedInKWS() {
        lisPushDisabledOverall();
    }

    @Override
    public void checkAllowedError() {
        lisNetworkErrorTryingToCheckUserStatus();
    }

    // KWSCheckRegisteredInterface

    @Override
    public void userIsRegistered() {
        lisPushAllowedOverall();
    }

    @Override
    public void userIsNotRegistered() {
        lisPushDisabledOverall();
    }

    @Override
    public void checkRegisteredError() {
        lisNetworkErrorTryingToCheckUserStatus();
    }

    // Listener functions

    void lisPushAllowedOverall () {
        if (listener != null) {
            listener.pushAllowedOverall();
        }
    }

    void lisPushDisabledOverall () {
        if (listener != null) {
            listener.pushDisabledOverall();
        }
    }

    void lisNetworkErrorTryingToCheckUserStatus () {
        if (listener != null) {
            listener.networkErrorTryingToCheckUserStatus();
        }
    }
}

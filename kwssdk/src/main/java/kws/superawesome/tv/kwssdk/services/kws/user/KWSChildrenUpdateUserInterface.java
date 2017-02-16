package kws.superawesome.tv.kwssdk.services.kws.user;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 25/08/16.
 */
public interface KWSChildrenUpdateUserInterface extends KWSServiceResponseInterface {
    void didUpdateUser (boolean updated);
}

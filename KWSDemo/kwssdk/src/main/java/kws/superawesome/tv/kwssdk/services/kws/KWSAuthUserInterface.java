package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 12/10/16.
 */
public interface KWSAuthUserInterface extends KWSServiceResponseInterface {
    void authUser (int status, KWSLoggedUser loggedUser);
}

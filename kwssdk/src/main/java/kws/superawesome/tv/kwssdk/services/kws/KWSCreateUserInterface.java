package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 08/10/16.
 */
public interface KWSCreateUserInterface extends KWSServiceResponseInterface {
    void created (int status, KWSLoggedUser loggedUser);
}

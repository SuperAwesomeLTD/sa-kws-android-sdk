package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public interface KWSGetUserInterface extends KWSServiceResponseInterface {
    public void gotUser(KWSUser user);
}

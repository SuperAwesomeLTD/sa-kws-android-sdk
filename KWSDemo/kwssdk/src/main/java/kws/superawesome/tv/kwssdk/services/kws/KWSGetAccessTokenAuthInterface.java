package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 12/10/16.
 */
public interface KWSGetAccessTokenAuthInterface extends KWSServiceResponseInterface {
    void gotToken (String token);
}

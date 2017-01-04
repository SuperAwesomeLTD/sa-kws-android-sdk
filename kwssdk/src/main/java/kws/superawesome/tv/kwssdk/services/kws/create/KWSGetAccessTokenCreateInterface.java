package kws.superawesome.tv.kwssdk.services.kws.create;

import kws.superawesome.tv.kwssdk.models.oauth.KWSAccessToken;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 11/10/16.
 */
public interface KWSGetAccessTokenCreateInterface extends KWSServiceResponseInterface {
    void gotToken (KWSAccessToken accessToken);
}

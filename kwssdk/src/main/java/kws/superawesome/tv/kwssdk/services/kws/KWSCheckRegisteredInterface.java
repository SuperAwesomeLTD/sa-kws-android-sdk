package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 14/07/16.
 */
public interface KWSCheckRegisteredInterface extends KWSServiceResponseInterface {
    void allowed (boolean registered);
}

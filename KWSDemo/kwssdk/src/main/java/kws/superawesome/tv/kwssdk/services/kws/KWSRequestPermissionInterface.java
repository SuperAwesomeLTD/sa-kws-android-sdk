package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSRequestPermissionInterface extends KWSServiceResponseInterface {
    void requested(boolean success, boolean requested);
}

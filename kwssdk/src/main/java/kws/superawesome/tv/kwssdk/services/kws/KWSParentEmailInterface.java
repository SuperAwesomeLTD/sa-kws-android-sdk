package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSParentEmailInterface extends KWSServiceResponseInterface {
    void submitted (KWSParentEmailStatus status);
}

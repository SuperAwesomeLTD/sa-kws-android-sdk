package kws.superawesome.tv.kwssdk.services.kws.parentemail;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSChildrenUpdateParentEmailInterface extends KWSServiceResponseInterface {
    void didUpdateParentEmail (KWSChildrenUpdateParentEmailStatus status);
}

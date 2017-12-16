package kws.superawesome.tv.kwssdk.services.kws.permissions;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSChildrenRequestPermissionInterface extends KWSServiceResponseInterface {
    void didRequestPermission (KWSChildrenRequestPermissionStatus status);
}

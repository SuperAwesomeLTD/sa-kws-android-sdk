package kws.superawesome.tv.kwssdk.services.kws.notifications;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSCheckAllowedInterface extends KWSServiceResponseInterface {
    void allowed (boolean allowed);
}

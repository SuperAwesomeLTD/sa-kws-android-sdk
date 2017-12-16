package kws.superawesome.tv.kwssdk.services.kws.score;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 08/08/16.
 */
public interface KWSChildrenTriggerEventInterface extends KWSServiceResponseInterface {
    void didTriggerEvent (boolean success);
}

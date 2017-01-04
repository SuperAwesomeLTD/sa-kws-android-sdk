package kws.superawesome.tv.kwssdk.services.kws.score;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public interface KWSHasTriggeredEventInterface extends KWSServiceResponseInterface {
    void hasTriggered(Boolean triggered);
}

package kws.superawesome.tv.kwssdk.services.kws.score;

import kws.superawesome.tv.kwssdk.services.KWSServiceInterface;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 08/08/16.
 */
public interface KWSTriggerEventInterface extends KWSServiceResponseInterface {
    void triggered(boolean success);
}

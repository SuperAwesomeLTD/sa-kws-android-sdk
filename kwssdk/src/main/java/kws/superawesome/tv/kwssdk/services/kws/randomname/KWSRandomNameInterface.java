package kws.superawesome.tv.kwssdk.services.kws.randomname;

import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 20/12/2016.
 */

public interface KWSRandomNameInterface extends KWSServiceResponseInterface {
    void gotRandomName (String name);
}

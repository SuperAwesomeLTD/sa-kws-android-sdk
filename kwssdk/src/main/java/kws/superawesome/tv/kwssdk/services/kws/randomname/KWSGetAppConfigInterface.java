package kws.superawesome.tv.kwssdk.services.kws.randomname;

import kws.superawesome.tv.kwssdk.models.appconfig.KWSAppConfig;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 04/01/2017.
 */

public interface KWSGetAppConfigInterface extends KWSServiceResponseInterface {
    void gotAppConfig (KWSAppConfig config);
}

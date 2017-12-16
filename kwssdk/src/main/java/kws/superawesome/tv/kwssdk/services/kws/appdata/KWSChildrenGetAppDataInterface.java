package kws.superawesome.tv.kwssdk.services.kws.appdata;

import java.util.List;

import kws.superawesome.tv.kwssdk.models.appdata.KWSAppData;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 25/08/16.
 */
public interface KWSChildrenGetAppDataInterface extends KWSServiceResponseInterface {
    void didGetAppData (List<KWSAppData> appData);
}

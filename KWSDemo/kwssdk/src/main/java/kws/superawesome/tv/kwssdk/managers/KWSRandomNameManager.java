package kws.superawesome.tv.kwssdk.managers;

import kws.superawesome.tv.kwssdk.kws.KWSGetAppConfig;
import kws.superawesome.tv.kwssdk.kws.KWSGetAppConfigInterface;
import kws.superawesome.tv.kwssdk.kws.KWSRandomName;
import kws.superawesome.tv.kwssdk.kws.KWSRandomNameInterface;
import kws.superawesome.tv.kwssdk.models.appconfig.KWSAppConfig;

/**
 * Created by gabriel.coman on 04/01/2017.
 */

public class KWSRandomNameManager {

    private KWSGetAppConfig getAppConfig;
    private KWSRandomName randomName;

    public KWSRandomNameManager () {
        getAppConfig = new KWSGetAppConfig();
        randomName = new KWSRandomName();
    }

    public void getRandomName (final KWSRandomNameInterface listener) {

        getAppConfig.execute(new KWSGetAppConfigInterface() {
            @Override
            public void gotAppConfig(KWSAppConfig config) {

                if (config != null) {
                    randomName.execute(config.id, listener);
                }
                else {
                    if (listener != null) {
                        listener.didGetRandomName(null);
                    }
                }

            }
        });

    }

}

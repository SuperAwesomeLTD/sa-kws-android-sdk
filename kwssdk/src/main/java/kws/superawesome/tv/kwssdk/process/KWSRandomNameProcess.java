package kws.superawesome.tv.kwssdk.process;

import android.content.Context;

import kws.superawesome.tv.kwssdk.models.appconfig.KWSAppConfig;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSGetAppConfig;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSGetAppConfigInterface;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSRandomName;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSChildrenGetRandomUsernameInterface;

public class KWSRandomNameProcess {

    private KWSGetAppConfig appConfig;
    private KWSRandomName randomName;

    public KWSRandomNameProcess () {
        appConfig = new KWSGetAppConfig();
        randomName = new KWSRandomName();
    }

    public void getRandomName(final Context context, final KWSChildrenGetRandomUsernameInterface listener) {

        appConfig.execute(context, new KWSGetAppConfigInterface() {
            @Override
            public void gotAppConfig(KWSAppConfig config) {

                // if the config is returned valid
                if (config != null) {

                    // finally getService the random name
                    randomName.execute(context, config.id, listener);

                }
                // if the config isn't valid, then return a nil random name
                else {
                    if (listener != null) {
                        listener.didGetRandomUsername(null);
                    }
                }

            }
        });

    }

}

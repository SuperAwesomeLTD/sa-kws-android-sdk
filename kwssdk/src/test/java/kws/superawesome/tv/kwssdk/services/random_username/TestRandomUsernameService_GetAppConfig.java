package kws.superawesome.tv.kwssdk.services.random_username;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.responses.AppConfig;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestRandomUsernameService_GetAppConfig extends TestRandomUsernameService {

    //given
    private String goodClientId = "good_client_id";
    private String badClientId = "bad_client_id";


    @Test
    public void testRandomUsernameServiceGetConfigOK() {

        KWSNetworkEnvironment goodEnvironment = new KWSNetworkEnvironment() {
            @NotNull
            @Override
            public String getAppID() {
                return goodClientId;
            }

            @NotNull
            @Override
            public String getMobileKey() {
                return environment.getMobileKey();
            }

            @NotNull
            @Override
            public String getDomain() {
                return environment.getDomain();
            }
        };

        service.getAppConfig(goodEnvironment, new Function2<AppConfig, Throwable, Unit>() {
            @Override
            public Unit invoke(AppConfig appConfig, Throwable throwable) {

                Assert.assertNotNull(appConfig);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testRandomUsernameServiceGetConfigBadClientId() {

        KWSNetworkEnvironment badEnvironment = new KWSNetworkEnvironment() {
            @NotNull
            @Override
            public String getAppID() {
                return badClientId;
            }

            @NotNull
            @Override
            public String getMobileKey() {
                return environment.getMobileKey();
            }

            @NotNull
            @Override
            public String getDomain() {
                return environment.getDomain();
            }
        };

        service.getAppConfig(badEnvironment, new Function2<AppConfig, Throwable, Unit>() {
            @Override
            public Unit invoke(AppConfig appConfig, Throwable throwable) {

                Assert.assertNull(appConfig);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

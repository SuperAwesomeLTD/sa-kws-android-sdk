package kws.superawesome.tv.kwssdk.providers;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.providers.RandomUsernameProvider;
import kws.superawesome.tv.kwssdk.base.responses.AppConfig;
import kws.superawesome.tv.kwssdk.base.responses.RandomUsername;

/**
 * Created by guilherme.mota on 16/01/2018.
 */

public class TestRandomUsernameProvider extends TestBaseProvider {


    //class to test
    private RandomUsernameProvider provider;

    //given
    private int goodAppId = 2;
    private int badAppId = 0;

    private String goodClientId = "good_client_id";
    private String badClientId = "bad_client_id";

    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //then
        // init class to test
        provider = new RandomUsernameProvider(environment, task);


    }


    @Test
    public void testRandomUsernameProviderGetConfigOK() {

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

        provider.getAppConfig(goodEnvironment, new Function2<AppConfig, Throwable, Unit>() {
            @Override
            public Unit invoke(AppConfig appConfig, Throwable throwable) {

                Assert.assertNotNull(appConfig);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testRandomUsernameProviderGetConfigBadClientId() {

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

        provider.getAppConfig(badEnvironment, new Function2<AppConfig, Throwable, Unit>() {
            @Override
            public Unit invoke(AppConfig appConfig, Throwable throwable) {

                Assert.assertNull(appConfig);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testRandomUsernameProviderRandomUsernameOK() {

        provider.getActuallyRandomUserName(environment, goodAppId, new Function2<RandomUsername, Throwable, Unit>() {
            @Override
            public Unit invoke(RandomUsername randomUsername, Throwable throwable) {

                Assert.assertNotNull(randomUsername);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testRandomUsernameProviderRandomUsernameBadAppId() {

        provider.getActuallyRandomUserName(environment, badAppId, new Function2<RandomUsername, Throwable, Unit>() {
            @Override
            public Unit invoke(RandomUsername randomUsername, Throwable throwable) {

                Assert.assertNull(randomUsername);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


}

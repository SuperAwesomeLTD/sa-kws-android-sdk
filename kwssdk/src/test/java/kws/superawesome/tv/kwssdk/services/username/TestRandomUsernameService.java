package kws.superawesome.tv.kwssdk.services.username;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.models.RandomUsername;
import kws.superawesome.tv.kwssdk.base.services.RandomUsernameService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;
import tv.superawesome.protobufs.features.auth.IUsernameService;
import tv.superawesome.protobufs.models.usernames.IRandomUsernameModel;

/**
 * Created by guilherme.mota on 16/01/2018.
 */

public class TestRandomUsernameService extends TestBaseService {

    //class to test
    protected IUsernameService service;

    private String goodClientId = "good_client_id";
    private String badClientId = "bad_client_id";

    @Override
    @Before
    public void setup() throws Throwable {

        //extended method from Base
        super.setup();


        //then
        // init class to test
        KWSSDK factory = new KWSSDK(environment, task);
        service = factory.get(IUsernameService.class);


    }


    @Test
    public void testServiceToNotBeNull() {
        Assert.assertNotNull(service);
    }


    @Test
    public void testServiceRandomUsernameOK() {

        KWSNetworkEnvironment goodNetworkEnvironment = new KWSNetworkEnvironment() {
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

        KWSSDK factory = new KWSSDK(goodNetworkEnvironment, task);
        service = factory.get(IUsernameService.class);

        service.getRandomUsername(new Function2<IRandomUsernameModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IRandomUsernameModel randomUsername, Throwable throwable) {

                Assert.assertNotNull(randomUsername);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testServiceRandomUsernameBadEnvironment() {


        KWSNetworkEnvironment badNetworkEnvironment = new KWSNetworkEnvironment() {
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

        KWSSDK factory = new KWSSDK(badNetworkEnvironment, task);
        service = factory.get(IUsernameService.class);

        service.getRandomUsername(new Function2<IRandomUsernameModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IRandomUsernameModel randomUsername, Throwable throwable) {

                Assert.assertNull(randomUsername);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}

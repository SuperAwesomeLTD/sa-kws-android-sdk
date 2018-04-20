package kws.superawesome.tv.kwssdk.username.services;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.ComplianceSDK;
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.TestBaseService;
import tv.superawesome.protobufs.features.auth.IUsernameService;
import tv.superawesome.protobufs.models.usernames.IRandomUsernameModel;

/**
 * Created by guilherme.mota on 16/01/2018.
 */

public class TestUsernameService_RandomUsername extends TestBaseService {

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
        ComplianceSDK sdk = new ComplianceSDK(environment, task);
        service = sdk.get(IUsernameService.class);

    }

    @Test
    public void test_UsernameService_Service_ToNot_BeNull() {
        Assert.assertNotNull(service);
    }


    @Test
    public void test_UsernameService_RandomUsername_OK() {

        NetworkEnvironment goodNetworkEnvironment = new NetworkEnvironment() {
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

        ComplianceSDK sdk = new ComplianceSDK(goodNetworkEnvironment, task);
        service = sdk.get(IUsernameService.class);

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
    public void test_UsernameService_RandomUsername_BadEnvironment() {


        NetworkEnvironment badNetworkEnvironment = new NetworkEnvironment() {
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

        ComplianceSDK sdk = new ComplianceSDK(badNetworkEnvironment, task);
        service = sdk.get(IUsernameService.class);

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

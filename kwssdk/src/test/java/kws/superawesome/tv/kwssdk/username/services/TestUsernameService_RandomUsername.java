package kws.superawesome.tv.kwssdk.username.services;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.ComplianceSDK;
import kws.superawesome.tv.kwssdk.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.TestBaseService;
import tv.superawesome.protobufs.usernames.models.IRandomUsernameModel;
import tv.superawesome.protobufs.usernames.services.IUsernameService;

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
        service = sdk.getService(IUsernameService.class);

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
            public String getClientID() {
                return goodClientId;
            }

            @NotNull
            @Override
            public String getClientSecret() {
                return environment.getClientSecret();
            }

            @NotNull
            @Override
            public String getDomain() {
                return environment.getDomain();
            }
        };

        ComplianceSDK sdk = new ComplianceSDK(goodNetworkEnvironment, task);
        service = sdk.getService(IUsernameService.class);

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
            public String getClientID() {
                return badClientId;
            }

            @NotNull
            @Override
            public String getClientSecret() {
                return environment.getClientSecret();
            }

            @NotNull
            @Override
            public String getDomain() {
                return environment.getDomain();
            }
        };

        ComplianceSDK sdk = new ComplianceSDK(badNetworkEnvironment, task);
        service = sdk.getService(IUsernameService.class);

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

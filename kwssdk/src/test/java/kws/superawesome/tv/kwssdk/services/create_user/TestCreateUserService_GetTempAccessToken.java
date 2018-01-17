package kws.superawesome.tv.kwssdk.services.create_user;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.responses.Login;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestCreateUserService_GetTempAccessToken extends TestCreateUserService {


    private String badClientId = "bad_client_id";
    private String badClientSecret = "bad_client_secret";

    private  //given
            KWSNetworkEnvironment badEnvironment = new KWSNetworkEnvironment() {
        @NotNull
        @Override
        public String getAppID() {
            return badClientId;
        }

        @NotNull
        @Override
        public String getMobileKey() {
            return badClientSecret;
        }

        @NotNull
        @Override
        public String getDomain() {
            return environment.getDomain();
        }
    };


    @Test
    public void testCreateUserServiceGetTempAccessTokenOK() {
        //then
        service.getTempAccessToken(environment, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNotNull(login);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }


    @Test
    public void testCreateUserServiceGetTempAccessTokenBadEnvironmentAppIdAndMobileKey() {


        //then
        service.getTempAccessToken(badEnvironment, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

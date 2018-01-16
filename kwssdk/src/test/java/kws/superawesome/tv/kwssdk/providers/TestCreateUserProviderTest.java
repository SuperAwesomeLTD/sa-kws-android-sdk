package kws.superawesome.tv.kwssdk.providers;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.providers.CreateUserProvider;
import kws.superawesome.tv.kwssdk.base.responses.CreateUser;
import kws.superawesome.tv.kwssdk.base.responses.Login;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestCreateUserProviderTest extends TestBaseProvider {

    //class to test
    private CreateUserProvider provider;

    //given
    private String goodUsername = "testuser123";
    private String badUsername = "bad_username";

    private String goodPassword = "testtest";
    private String badPassword = "bad_password";

    private String goodDOB = "2012-03-03";
    private String badDOB = "bad_dob";

    private String goodCountry = "US";
    private String badCountry = "bad_country";

    private String goodParentEmail = "good_parent@email.com";
    private String badParentEmail = "bad_parent";

    private String goodMockedToken = "good_token";
    private String badMockedToken = "bad_token";

    private String badClientId = "bad_client_id";

    private String badClientSecret = "bad_client_secret";

    private int goodAppId = 2;
    private int badAppId = 0;

    @Before
    public void setup() throws Throwable {


        //extended method from Base
        prepareMockedClient();


        //when
        // init class to test
        provider = new CreateUserProvider(environment, task);


    }

    @Test
    public void testCreateUserProviderDoUserCreationOK() {
        //then
        provider.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNotNull(createUser);
                        Assert.assertNull(throwable);

                        return null;
                    }
                });


    }

    @Test
    public void testCreateUserProviderDoUserCreationBadUsername() {
        //then
        provider.doUserCreation(environment, badUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserProviderDoUserCreationBadNullUsername() {
        //then
        provider.doUserCreation(environment, null, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test
    public void testCreateUserProviderDoUserCreationBadPassword() {
        //then
        provider.doUserCreation(environment, goodUsername, badPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserProviderDoUserCreationBadNullPassword() {
        //then
        provider.doUserCreation(environment, goodUsername, null, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test
    public void testCreateUserProviderDoUserCreationBadDOB() {
        //then
        provider.doUserCreation(environment, goodUsername, goodPassword, badDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserProviderDoUserCreationBadCountry() {
        //then
        provider.doUserCreation(environment, goodUsername, goodPassword, goodDOB, badCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });

    }


    @Test
    public void testCreateUserProviderDoUserCreationBadParentEmail() {
        //then
        provider.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                badParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserProviderDoUserCreationBadAppId() {
        //then
        provider.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, badAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserProviderDoUserCreationBadToken() {
        //then
        provider.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, badMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserProviderDoUserCreationBadAppIdAndToken() {
        //then
        provider.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, badAppId, badMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test
    public void testCreateUserProviderGetTempAccessTokenOK() {
        //then
        provider.getTempAccessToken(environment, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNotNull(login);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }


    @Test
    public void testCreateUserProviderGetTempAccessTokenBadEnvironmentAppIdAndMobileKey() {
        //given
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

        //then
        provider.getTempAccessToken(badEnvironment, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

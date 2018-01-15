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

    private String goodUsername, badUsername, goodPassword, badPassword, goodDOB,
            badDOB, goodCountry, badCountry, goodParentEmail, badParentEmail, goodMockedToken, badMockedToken;
    private int goodAppId, badAppId;

    @Before
    public void setup() throws Throwable {


        //extended method from Base
        prepareMockedClient();

        //given
        goodUsername = "testuser123";
        badUsername = "bad_username";

        goodPassword = "testtest";
        badPassword = "bad_password";

        goodDOB = "2012-03-03";
        badDOB = "bad_dob";

        goodCountry = "US";
        badCountry = "bad_country";

        goodParentEmail = "good_parent@email.com";
        badParentEmail = "bad_parent";

        goodMockedToken = "good_token";
        badMockedToken = "bad_token";

        goodAppId = 2;
        badAppId = 0;

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
    public void testCreateUserProviderGetTempAccessTokenBadEnvironment() {
        //given
        KWSNetworkEnvironment testEnvironment = new KWSNetworkEnvironment() {
            @NotNull
            @Override
            public String getAppID() {
                return "";
            }

            @NotNull
            @Override
            public String getMobileKey() {
                return "";
            }

            @NotNull
            @Override
            public String getDomain() {
                return environment.getDomain();
            }
        };

        //then
        provider.getTempAccessToken(testEnvironment, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

package kws.superawesome.tv.kwssdk.services.create_user;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.responses.CreateUser;
import kws.superawesome.tv.kwssdk.base.services.CreateUserService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestCreateUserService extends TestBaseService {

    //class to test
    protected CreateUserService service;

    //given
    protected String goodMockedToken = "good_token";
    protected String badMockedToken = "bad_token";

    protected int goodAppId = 2;
    protected int badAppId = 0;

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

    private String badClientId = "bad_client_id";
    private String badClientSecret = "bad_client_secret";

    @Override
    @Before
    public void setup() throws Throwable {

        //extended method from Base
        super.setup();

        //when
        // init class to test
        service = KWSSDK.get(environment, CreateUserService.class, task);


    }

    @Test
    public void testServiceToNotBeNull() {
        Assert.assertNotNull(service);
    }


    @Test
    public void testCreateUserServiceOK() {

        service.createUser(goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNotNull(createUser);
                        Assert.assertNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void testCreateUserServiceBadEnvironment() {

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

        service = KWSSDK.get(badEnvironment, CreateUserService.class, task);
        service.createUser(goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void testCreateUserServiceBadUsername() {

        service.createUser(badUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void testCreateUserServiceBadPassword() {

        service.createUser(goodUsername, badPassword, goodDOB, goodCountry,
                goodParentEmail, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void testCreateUserServiceBadDOB() {

        service.createUser(goodUsername, goodPassword, badDOB, goodCountry,
                goodParentEmail, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void testCreateUserServiceBadCountry() {

        service.createUser(goodUsername, goodPassword, goodDOB, badCountry,
                goodParentEmail, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }


    @Test
    public void testCreateUserServiceBadParentEmail() {

        service.createUser(goodUsername, goodPassword, goodDOB, badCountry,
                badParentEmail, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }


}

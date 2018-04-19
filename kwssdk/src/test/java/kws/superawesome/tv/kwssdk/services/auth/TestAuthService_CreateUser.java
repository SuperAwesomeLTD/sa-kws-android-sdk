package kws.superawesome.tv.kwssdk.services.auth;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment;
import tv.superawesome.protobufs.features.auth.IAuthService;
import tv.superawesome.protobufs.models.auth.ILoggedUserModel;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestAuthService_CreateUser extends TestAuthService {

    //given
    protected String goodMockedToken = "good_token";
    protected String badMockedToken = "bad_token";

    protected int goodAppId = 2;
    protected int badAppId = 0;

    private String timezone = null;

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


    }

    @Test
    public void test_Service_ToNot_BeNull() {
        Assert.assertNotNull(service);
    }


    @Test
    public void test_AuthService_CreateUser_Service_OK() {

        service.createUser(goodUsername, goodPassword, timezone, goodDOB, goodCountry,
                goodParentEmail, new Function2<ILoggedUserModel, Throwable, Unit>() {
                    @Override
                    public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                        Assert.assertNotNull(loggedUserModel);
                        Assert.assertNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void test_AuthService_CreateUser_Service_BadEnvironment() {

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

        KWSSDK factory = new KWSSDK(badEnvironment, task);
        service = factory.get(IAuthService.class);

        service.createUser(goodUsername, goodPassword, timezone, goodDOB, goodCountry,
                goodParentEmail, new Function2<ILoggedUserModel, Throwable, Unit>() {
                    @Override
                    public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                        Assert.assertNull(loggedUserModel);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void test_AuthService_CreateUser_Service_BadUsername() {

        service.createUser(badUsername, goodPassword, timezone, goodDOB, goodCountry,
                goodParentEmail, new Function2<ILoggedUserModel, Throwable, Unit>() {
                    @Override
                    public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                        Assert.assertNull(loggedUserModel);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void test_AuthService_CreateUser_Service_BadPassword() {

        service.createUser(goodUsername, badPassword, timezone, goodDOB, goodCountry,
                goodParentEmail, new Function2<ILoggedUserModel, Throwable, Unit>() {
                    @Override
                    public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                        Assert.assertNull(loggedUserModel);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void test_AuthService_CreateUser_Service_BadDOB() {

        service.createUser(goodUsername, goodPassword, timezone, badDOB, goodCountry,
                goodParentEmail, new Function2<ILoggedUserModel, Throwable, Unit>() {
                    @Override
                    public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                        Assert.assertNull(loggedUserModel);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }

    @Test
    public void test_AuthService_CreateUser_Service_BadCountry() {

        service.createUser(goodUsername, goodPassword, timezone, goodDOB, badCountry,
                goodParentEmail, new Function2<ILoggedUserModel, Throwable, Unit>() {
                    @Override
                    public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                        Assert.assertNull(loggedUserModel);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }


    @Test
    public void test_AuthService_CreateUser_Service_Bad_Parent_Email() {

        service.createUser(goodUsername, goodPassword, timezone, goodDOB, badCountry,
                badParentEmail, new Function2<ILoggedUserModel, Throwable, Unit>() {
                    @Override
                    public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                        Assert.assertNull(loggedUserModel);
                        Assert.assertNotNull(throwable);


                        return null;
                    }
                });

    }


}

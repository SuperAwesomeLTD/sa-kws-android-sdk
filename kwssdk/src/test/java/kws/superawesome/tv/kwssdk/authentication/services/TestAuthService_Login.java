package kws.superawesome.tv.kwssdk.authentication.services;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.models.auth.ILoggedUserModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestAuthService_Login extends TestAuthService {

    @Test
    public void test_AuthService_LoginProvider_LoginOK() throws Throwable {
        // when
        service.loginUser(goodUsername, goodPassword, new Function2<ILoggedUserModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                // then
                Assert.assertNotNull(loggedUserModel);
                Assert.assertNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test_AuthService_LoginProvider_Login_BadUsername() throws Throwable {
        // when
        service.loginUser(badUsername, goodPassword, new Function2<ILoggedUserModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                Assert.assertNull(loggedUserModel);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test_AuthService_LoginProvider_Login_BadPassword() throws Throwable {
        // when
        service.loginUser(goodUsername, badPassword, new Function2<ILoggedUserModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                Assert.assertNull(loggedUserModel);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_AuthService_LoginProvider_Login_Fail_Null_Username() throws Throwable {
        // when
        service.loginUser(null, goodPassword, new Function2<ILoggedUserModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(loggedUserModel);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AuthService_Login_Provider_Login_Fail_Null_Password() throws Throwable {
        // when
        service.loginUser(goodUsername, null, new Function2<ILoggedUserModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(loggedUserModel);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test
    public void test_AuthService_LoginProvider_Login_Fail_Empty_Username() throws Throwable {
        // when
        service.loginUser("", goodPassword, new Function2<ILoggedUserModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                Assert.assertNull(loggedUserModel);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test_AuthService_LoginProvider_Login_Fail_Empty_Password() throws Throwable {
        // when
        service.loginUser(goodUsername, "", new Function2<ILoggedUserModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILoggedUserModel loggedUserModel, Throwable throwable) {

                Assert.assertNull(loggedUserModel);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


}

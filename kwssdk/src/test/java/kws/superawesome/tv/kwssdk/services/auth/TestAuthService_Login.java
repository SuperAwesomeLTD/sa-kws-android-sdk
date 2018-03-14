package kws.superawesome.tv.kwssdk.services.auth;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestAuthService_Login extends TestAuthService {

    @Test
    public void testLoginProviderLoginOK() throws Throwable {
        // when
        service.loginUser(goodUsername, goodPassword, new Function2<LoginAuthResponse, Throwable, Unit>() {
            @Override
            public Unit invoke(LoginAuthResponse loginAuthResponse, Throwable throwable) {

                // then
                Assert.assertNotNull(loginAuthResponse);
                Assert.assertNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderLoginBadUsername() throws Throwable {
        // when
        service.loginUser(badUsername, goodPassword, new Function2<LoginAuthResponse, Throwable, Unit>() {
            @Override
            public Unit invoke(LoginAuthResponse loginAuthResponse, Throwable throwable) {

                Assert.assertNull(loginAuthResponse);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderLoginBadPassword() throws Throwable {
        // when
        service.loginUser(goodUsername, badPassword, new Function2<LoginAuthResponse, Throwable, Unit>() {
            @Override
            public Unit invoke(LoginAuthResponse loginAuthResponse, Throwable throwable) {

                Assert.assertNull(loginAuthResponse);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderLoginFailNullUsername() throws Throwable {
        // when
        service.loginUser(null, goodPassword, new Function2<LoginAuthResponse, Throwable, Unit>() {
            @Override
            public Unit invoke(LoginAuthResponse loginAuthResponse, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(loginAuthResponse);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderLoginFailNullPassword() throws Throwable {
        // when
        service.loginUser(goodUsername, null, new Function2<LoginAuthResponse, Throwable, Unit>() {
            @Override
            public Unit invoke(LoginAuthResponse loginAuthResponse, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(loginAuthResponse);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test
    public void testLoginProviderLoginFailEmptyUsername() throws Throwable {
        // when
        service.loginUser("", goodPassword, new Function2<LoginAuthResponse, Throwable, Unit>() {
            @Override
            public Unit invoke(LoginAuthResponse loginAuthResponse, Throwable throwable) {

                Assert.assertNull(loginAuthResponse);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderLoginFailEmptyPassword() throws Throwable {
        // when
        service.loginUser(goodUsername, "", new Function2<LoginAuthResponse, Throwable, Unit>() {
            @Override
            public Unit invoke(LoginAuthResponse loginAuthResponse, Throwable throwable) {

                Assert.assertNull(loginAuthResponse);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


}

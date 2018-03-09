package kws.superawesome.tv.kwssdk.services.login;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.Login;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestLoginService_Login extends TestLoginService {

    @Test
    public void testLoginProviderLoginOK() throws Throwable {
        // when
        service.loginUser(goodUsername, goodPassword, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                // then
                Assert.assertNotNull(login);
                Assert.assertNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderLoginBadUsername() throws Throwable {
        // when
        service.loginUser(badUsername, goodPassword, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderLoginBadPassword() throws Throwable {
        // when
        service.loginUser(goodUsername, badPassword, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderLoginFailNullUsername() throws Throwable {
        // when
        service.loginUser(null, goodPassword, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderLoginFailNullPassword() throws Throwable {
        // when
        service.loginUser(goodUsername, null, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test
    public void testLoginProviderLoginFailEmptyUsername() throws Throwable {
        // when
        service.loginUser("", goodPassword, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderLoginFailEmptyPassword() throws Throwable {
        // when
        service.loginUser(goodUsername, "", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


}

package kws.superawesome.tv.kwssdk.providers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.providers.LoginProvider;
import kws.superawesome.tv.kwssdk.base.responses.Login;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class TestLoginProvider extends BaseProvider{

    // class to test
    private LoginProvider provider;

    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();

        // init class to test
        provider = new LoginProvider(environment, task);
    }

    @Test
    public void testLoginProviderBadUsername() throws Throwable {
        // when
        provider.loginUser("bad_username", "testtest", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderBadPassword() throws Throwable {
        // when
        provider.loginUser("testuser123", "bad_password", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderOK() throws Throwable {
        // when
        provider.loginUser("testuser123", "testtest", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                // then
                Assert.assertNotNull(login);
                Assert.assertNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderFailNullUsername() throws Throwable {
        // when
        provider.loginUser(null, "testtest", new Function2<Login, Throwable, Unit>() {
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
    public void testLoginProviderFailNullPassword() throws Throwable {
        // when
        provider.loginUser("testuser123", null, new Function2<Login, Throwable, Unit>() {
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
    public void testLoginProviderFailEmptyUsername() throws Throwable {
        // when
        provider.loginUser("", "testtest", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testLoginProviderFailEmptyPassword() throws Throwable {
        // when
        provider.loginUser("testuser123", "", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @After
    public void unsetup() throws Throwable {
        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

}

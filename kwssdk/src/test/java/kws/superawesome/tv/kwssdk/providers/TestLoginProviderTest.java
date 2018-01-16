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

public class TestLoginProviderTest extends TestBaseProvider {

    // class to test
    private LoginProvider provider;

    private String goodUsername, badUsername, goodPassword, badPassword;

    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();

        //given
        goodUsername = "good_username";
        badUsername = "bad_username";

        goodPassword = "good_password";
        badPassword = "bad_password";

        //then
        // init class to test
        provider = new LoginProvider(environment, task);
    }

    @Test
    public void testLoginProviderOK() throws Throwable {
        // when
        provider.loginUser(goodUsername, goodPassword, new Function2<Login, Throwable, Unit>() {
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
    public void testLoginProviderBadUsername() throws Throwable {
        // when
        provider.loginUser(badUsername, goodPassword, new Function2<Login, Throwable, Unit>() {
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
        provider.loginUser(goodUsername, badPassword, new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderFailNullUsername() throws Throwable {
        // when
        provider.loginUser(null, goodPassword, new Function2<Login, Throwable, Unit>() {
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
        provider.loginUser(goodUsername, null, new Function2<Login, Throwable, Unit>() {
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
        provider.loginUser("", goodPassword, new Function2<Login, Throwable, Unit>() {
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
        provider.loginUser(goodUsername, "", new Function2<Login, Throwable, Unit>() {
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

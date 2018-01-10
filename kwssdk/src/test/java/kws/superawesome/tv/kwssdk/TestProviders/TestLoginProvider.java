package kws.superawesome.tv.kwssdk.TestProviders;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executor;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.models.LoggedUser;
import kws.superawesome.tv.kwssdk.base.providers.LoginProvider;
import kws.superawesome.tv.kwssdk.base.responses.Login;
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata;
import tv.superawesome.samobilebase.aux.network.SANetwork;
import tv.superawesome.samobilebase.network.NetworkTask;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class TestLoginProvider {

    // class to test
    private LoginProvider provider;

    // mocks
    private KWSNetworkEnvironment environment;
    private SANetwork network;
    private NetworkTask task;
    private MockKWSServer server;
    private Executor executor;

    //same as the one in `mock_login_success_response`
    private String mockedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjI1LCJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc3QiLCJzY29wZSI6InVzZXIiLCJpYXQiOjE1MTU2MDY3ODgsImV4cCI6MTUxNTY5MzE4OCwiaXNzIjoic3VwZXJhd2Vzb21lIn0." +
            "G4eviyQZK9KOBycggFiabHlhegkmrysS05j28e-hfZw";

    @Before
    public void setup() throws Throwable {
        // create server
        server = new MockKWSServer();

        // start the server instance
        server.start();

        // create environment
        environment = new MockEnvironment(server.url());

        // start mock executor
        executor = new MockExecutor();

        // start semi-mocked SANetwork class
        network = new SANetwork(executor);

        // init semi-mocked network task
        task = new NetworkTask(network);

        // init class to test
        provider = new LoginProvider(environment, task);
    }

    @Test
    public void testLoginProviderBadUsername() throws Throwable {
        // when
        provider.loginUser("bad_username", "testtest", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                // then
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

                // then
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

                //create metadata
                String token = login.getToken();
                KWSMetadata kwsMetadata = KWSMetadata.processMetadata(token);

                LoggedUser loggedUser = null;
                if (kwsMetadata != null && kwsMetadata.isValid()) {
                    loggedUser = new LoggedUser(token, kwsMetadata);
                }
                Assert.assertNotNull(loggedUser);
                Assert.assertNotNull(loggedUser.getToken());
                Assert.assertEquals(mockedToken, loggedUser.getToken());
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

                // then
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

                // then
                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderFailEmptyUsername() throws Throwable {
        // when
        provider.loginUser("", "testtest", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                // then
                Assert.assertNull(login);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void testLoginProviderFailEmptyPassword() throws Throwable {
        // when
        provider.loginUser("testuser123", "", new Function2<Login, Throwable, Unit>() {
            @Override
            public Unit invoke(Login login, Throwable throwable) {

                // then
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

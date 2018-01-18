package kws.superawesome.tv.kwssdk.services;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Executor;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;

import tv.superawesome.samobilebase.network.NetworkTask;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestBaseService {

    // mocks
    protected KWSNetworkEnvironment environment;
    protected MockKWSServer server;
    protected NetworkTask task;

    @Before
    protected void setup() throws Throwable {

        // create server
        server = new MockKWSServer();

        // start the server instance
        server.start();

        // create environment
        environment = new MockEnvironment(server.url());

        // start mock executor
        Executor executor = new MockExecutor();

        // init semi-mocked network task
        task = new NetworkTask(executor);

    }

    @After
    public void tearDown() throws Throwable {
        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

}

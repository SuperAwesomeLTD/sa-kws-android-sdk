package kws.superawesome.tv.kwssdk.services;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Executor;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import tv.superawesome.samobilebase.network.NetworkTask;

/**
 * Created by guilherme.mota on 19/03/2018.
 */

abstract public class AbstractTestService {

    // mock data
    protected MockAbstractWebServer server;
    protected KWSNetworkEnvironment environment;
    protected NetworkTask task;

    abstract MockAbstractWebServer getServer();

    abstract KWSNetworkEnvironment getEnvironment();

    @Before
    public void setup() throws Throwable {
        // given
        server = getServer();
        server.start();
        environment = getEnvironment();
        Executor executor = new MockExecutor();
        task = new NetworkTask(executor);
    }

    @After
    public void tearDown() throws Throwable {
        server.shutdown();
    }
}

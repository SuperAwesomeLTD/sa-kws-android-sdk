package kws.superawesome.tv.kwssdk.providers;

import java.util.concurrent.Executor;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import tv.superawesome.samobilebase.aux.network.SANetwork;
import tv.superawesome.samobilebase.network.NetworkTask;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestBaseProvider {

    // mocks
    protected KWSNetworkEnvironment environment;
    protected MockKWSServer server;
    protected NetworkTask task;

    private SANetwork network;
    private Executor executor;

    protected void prepareMockedClient() throws Throwable {

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

    }

}

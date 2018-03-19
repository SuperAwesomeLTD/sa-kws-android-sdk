package kws.superawesome.tv.kwssdk.services;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Executor;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;

import tv.superawesome.samobilebase.network.NetworkTask;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestBaseService extends AbstractTestService {

    @Override
    MockAbstractWebServer getServer() {
        return new MockKWSServer();
    }

    @Override
    KWSNetworkEnvironment getEnvironment() {
        return new MockEnvironment(super.server.url());
    }

}

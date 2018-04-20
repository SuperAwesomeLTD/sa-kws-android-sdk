package kws.superawesome.tv.kwssdk.services;

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment;

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

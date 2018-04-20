package kws.superawesome.tv.kwssdk;

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestBaseService extends AbstractTestService {

    @Override
    MockAbstractWebServer getServer() {
        return new MockServer();
    }

    @Override
    NetworkEnvironment getEnvironment() {
        return new MockEnvironment(super.server.url());
    }

}

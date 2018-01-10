package kws.superawesome.tv.kwssdk.TestProviders;

import org.jetbrains.annotations.NotNull;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class MockEnvironment implements KWSNetworkEnvironment {

    private String url;

    MockEnvironment(String url) {
        this.url = url;
    }

    @NotNull
    @Override
    public String getDomain() {
        return null;
    }

    @NotNull
    @Override
    public String getAppID() {
        return "stan-test";
    }

    @NotNull
    @Override
    public String getMobileKey() {
        return "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4";
    }
}

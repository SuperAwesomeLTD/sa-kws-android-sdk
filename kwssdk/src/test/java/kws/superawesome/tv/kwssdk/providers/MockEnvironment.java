package kws.superawesome.tv.kwssdk.providers;

import org.jetbrains.annotations.NotNull;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class MockEnvironment implements KWSNetworkEnvironment {

    private String url;
    private static final String APPID = "stan-test"; // "superawesomeclub";
    private static final String KEY = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4";

    MockEnvironment(String url) {
        this.url = url;
    }

    @NotNull
    @Override
    public String getDomain() {
        return url;
    }

    @NotNull
    @Override
    public String getAppID() {
        return APPID;
    }

    @NotNull
    @Override
    public String getMobileKey() {
        return KEY;
    }
}

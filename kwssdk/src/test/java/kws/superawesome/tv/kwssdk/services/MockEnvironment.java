package kws.superawesome.tv.kwssdk.services;

import org.jetbrains.annotations.NotNull;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class MockEnvironment implements KWSNetworkEnvironment {

    private String url;
    private static final String APPID = "kws-sdk-testing"; // "superawesomeclub"; //"stan-test"
    private static final String KEY = "TKZpmBq3wWjSuYHN27Id0hjzN4cIL13D";

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

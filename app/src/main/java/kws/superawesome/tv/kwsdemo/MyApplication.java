package kws.superawesome.tv.kwsdemo;

import android.support.multidex.MultiDexApplication;

import kws.superawesome.tv.kwssdk.KWSChildren;

public class MyApplication extends MultiDexApplication {

    //stan test
//    private static final String APPID = "stan-test"; // "superawesomeclub";
//    private static final String KEY = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4";
//    private static final String URL = "https://stan-test-cluster.api.kws.superawesome.tv/";

    //demo env
    private static final String APPID = "kws-sdk-testing";
    private static final String KEY = "TKZpmBq3wWjSuYHN27Id0hjzN4cIL13D";
    private static final String URL = "https://kwsapi.demo.superawesome.tv/";

    @Override
    public void onCreate() {
        super.onCreate();

        KWSChildren.sdk.setup(this, APPID, KEY, URL);
    }
}

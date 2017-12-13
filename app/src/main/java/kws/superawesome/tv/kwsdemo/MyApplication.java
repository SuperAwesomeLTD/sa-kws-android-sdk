package kws.superawesome.tv.kwsdemo;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import kws.superawesome.tv.kwssdk.KWSChildren;

public class MyApplication extends MultiDexApplication {

    private static final String APPID = "stan-test"; // "superawesomeclub";
    private static final String KEY = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4";
    private static final String URL = "https://stan-test-cluster.api.kws.superawesome.tv/";

    @Override
    public void onCreate() {
        super.onCreate();

        KWSChildren.sdk.setup(this, APPID, KEY, URL);
    }
}

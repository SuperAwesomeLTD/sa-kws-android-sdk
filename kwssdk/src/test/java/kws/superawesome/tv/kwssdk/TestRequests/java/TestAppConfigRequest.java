package kws.superawesome.tv.kwssdk.TestRequests.java;

import junit.framework.Assert;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestAppConfigRequest {

    //class to be tested
    AppConfigRequest appConfigRequest;


    private final String APPID = "stan-test";
    private final String KEY = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4";
    private final String URL = "https://stan-test-cluster.api.kws.superawesome.tv/";
    @NotNull
    private final KWSNetworkEnvironment kwsNetworkEnvironmnet = (KWSNetworkEnvironment) (new KWSNetworkEnvironment() {
        @NotNull
        public String getAppID() {
            return APPID;
        }

        @NotNull
        public String getMobileKey() {
            return KEY;
        }

        @NotNull
        public String getDomain() {
            return URL;
        }
    });

    @NotNull
    public final KWSNetworkEnvironment getKwsNetworkEnvironmnet() {
        return this.kwsNetworkEnvironmnet;
    }

    @Test
    public final void GetAppConfigRequest_Valid() {
        //given
        String clientID = "stan-test";
        String endpoint = "v1/apps/config";
        NetworkMethod method = NetworkMethod.GET;

        //when
        appConfigRequest = new AppConfigRequest(this.kwsNetworkEnvironmnet, this.kwsNetworkEnvironmnet.getAppID());


        //then
        Assert.assertNotNull(appConfigRequest);
        Assert.assertNotNull(clientID);

        MatcherAssert.assertThat(appConfigRequest.getHeaders(), IsMapContaining.hasEntry("Content-Type", "application/json"));

        Assert.assertEquals(appConfigRequest.getEndpoint(), endpoint);
        Assert.assertEquals(appConfigRequest.getMethod(), method);

        Map<String, Object> query = appConfigRequest.getQuery();

        Assert.assertEquals(query.size(), 1);
        Assert.assertTrue(query.containsKey("oauthClientId"));

        Assert.assertNull(appConfigRequest.getBody());
        Assert.assertFalse(appConfigRequest.getFormEncodeUrls());
    }
}

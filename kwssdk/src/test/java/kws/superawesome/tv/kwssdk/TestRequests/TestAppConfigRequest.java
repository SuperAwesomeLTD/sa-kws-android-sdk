package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestAppConfigRequest {

    //class to be tested
    private AppConfigRequest appConfigRequest;

    //mocks
    private KWSNetworkEnvironment environment;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);
    }


    @Test
    public final void testRequest() {

        //given
        String clientID = "stan-test";
        String endpoint = "v1/apps/config";
        NetworkMethod method = NetworkMethod.GET;

        //when
        appConfigRequest = new AppConfigRequest(
                environment,
                environment.getAppID());


        //then
        Assert.assertNotNull(appConfigRequest);
        Assert.assertNotNull(clientID);
        Assert.assertEquals(endpoint, appConfigRequest.getEndpoint());
        Assert.assertEquals(method, appConfigRequest.getMethod());

        Map<String, Object> body = appConfigRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = appConfigRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

        Map<String, Object> query = appConfigRequest.getQuery();

        Assert.assertNotNull(query);
        Assert.assertEquals(query.size(), 1);
        Assert.assertTrue(query.containsKey("oauthClientId"));


        Assert.assertFalse(appConfigRequest.getFormEncodeUrls());
    }
}

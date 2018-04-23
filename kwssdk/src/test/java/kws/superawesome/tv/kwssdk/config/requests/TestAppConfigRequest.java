package kws.superawesome.tv.kwssdk.config.requests;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.config.requests.AppConfigRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestAppConfigRequest {

    //class to be tested
    private AppConfigRequest appConfigRequest;

    //mocks
    private NetworkEnvironment environment;
    private String clientID;
    private String endpoint;
    private NetworkMethod method;

    @Test
    public void test_App_Config_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        clientID = "__mock_clientId__";
        endpoint = "v1/apps/config";
        method = NetworkMethod.GET;

        //when
        appConfigRequest = new AppConfigRequest(
                environment,
                clientID);

        //then
        Assert.assertNotNull(clientID);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(appConfigRequest);
        Assert.assertNotNull(appConfigRequest.getEnvironment());

        Assert.assertEquals(method, appConfigRequest.getMethod());
        Assert.assertEquals(endpoint, appConfigRequest.getEndpoint());

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

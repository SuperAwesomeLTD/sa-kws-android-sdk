package kws.superawesome.tv.kwssdk.requests;

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
    private String clientID;
    private String endpoint;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        clientID = "__mock_clientId__";
        endpoint = "v1/apps/config";
        method = NetworkMethod.GET;

        //when
        appConfigRequest = new AppConfigRequest(
                environment,
                clientID);

    }


    @Test
    public void testConstants() {

        Assert.assertNotNull(clientID);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }


    @Test
    public final void testRequest() {
        Assert.assertNotNull(appConfigRequest);

    }

    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(appConfigRequest.getEnvironment());

    }

    @Test
    public void testMethod() {
        Assert.assertEquals(method, appConfigRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, appConfigRequest.getEndpoint());
    }


    @Test
    public void testBody() {
        Map<String, Object> body = appConfigRequest.getBody();

        Assert.assertNull(body);
    }


    @Test
    public void testHeader() {
        Map<String, String> header = appConfigRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));
    }

    @Test
    public void testQuery() {
        Map<String, Object> query = appConfigRequest.getQuery();

        Assert.assertNotNull(query);
        Assert.assertEquals(query.size(), 1);
        Assert.assertTrue(query.containsKey("oauthClientId"));
    }

    @Test
    public void testFormEncodedURLs() {
        Assert.assertFalse(appConfigRequest.getFormEncodeUrls());
    }


}

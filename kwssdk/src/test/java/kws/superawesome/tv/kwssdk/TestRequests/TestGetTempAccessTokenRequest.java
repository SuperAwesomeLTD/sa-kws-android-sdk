package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestGetTempAccessTokenRequest {

    //class to be tested
    private TempAccessTokenRequest tempAccessTokenRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private String clientId, clientSecret, endpoint;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        clientId = "__mock_clientId__";
        clientSecret = "__mock_clientSecret__";
        endpoint = "oauth/token";
        method = NetworkMethod.POST;

        setRequestConstruct();
    }

    @Test
    public void testConstants() {

        Assert.assertNotNull(clientId);
        Assert.assertNotNull(clientSecret);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        setRequestConstruct();
    }

    public void setRequestConstruct() {
        //when
        tempAccessTokenRequest = new TempAccessTokenRequest(
                environment,
                clientId,
                clientSecret);
    }


    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(tempAccessTokenRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(tempAccessTokenRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, tempAccessTokenRequest.getMethod());

    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, tempAccessTokenRequest.getEndpoint());
    }


    @Test
    public final void testBody() {

        Map<String, Object> body = tempAccessTokenRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 3);
        Assert.assertTrue(body.containsKey("grant_type"));
        Assert.assertTrue(body.containsKey("client_id"));
        Assert.assertTrue(body.containsKey("client_secret"));
        Assert.assertEquals("client_credentials", body.get("grant_type"));
        Assert.assertEquals(clientId, body.get("client_id"));
        Assert.assertEquals(clientSecret, body.get("client_secret"));

    }

    @Test
    public final void testHeader() {
        Map<String, String> header = tempAccessTokenRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/x-www-form-urlencoded", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = tempAccessTokenRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertTrue(tempAccessTokenRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        tempAccessTokenRequest = null;
    }

}

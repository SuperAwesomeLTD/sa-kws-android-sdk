package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.GetAppDataRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestGetAppDataRequest {


    //class to be tested
    private GetAppDataRequest getAppDataRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private String token, endpoint;
    private int appId, userId;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        appId = 1;
        userId = 1;
        endpoint = "v1/apps/" + appId + "/users/" + userId + "/app-data";
        method = NetworkMethod.GET;
        token = "__mock_token__";

        //when
        getAppDataRequest = new GetAppDataRequest(
                environment,
                appId,
                userId,
                token
        );
    }

    @Test
    public void testConstants() {
        Assert.assertTrue(appId > -1);
        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
    }


    @Test
    public final void testRequest() {
        Assert.assertNotNull(getAppDataRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(getAppDataRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, getAppDataRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, getAppDataRequest.getEndpoint());
    }

    @Test
    public final void testBody() {
        Map<String, Object> body = getAppDataRequest.getBody();
        Assert.assertNull(body);
    }

    @Test
    public final void testHeader() {
        Map<String, String> header = getAppDataRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));
    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = getAppDataRequest.getQuery();
        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(getAppDataRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        getAppDataRequest = null;
    }


}

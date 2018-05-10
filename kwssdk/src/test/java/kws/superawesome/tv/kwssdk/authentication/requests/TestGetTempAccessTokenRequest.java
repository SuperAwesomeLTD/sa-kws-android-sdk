package kws.superawesome.tv.kwssdk.authentication.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.NetworkEnvironment;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestGetTempAccessTokenRequest {

    //class to be tested
    private TempAccessTokenRequest tempAccessTokenRequest;

    //mocks
    private NetworkEnvironment environment;
    private String clientId, clientSecret, endpoint;
    private NetworkMethod method;

    @Test
    public void test_GetTempAccessToken_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        clientId = "__mock_clientId__";
        clientSecret = "__mock_clientSecret__";
        endpoint = "oauth/token";
        method = NetworkMethod.POST;

        //when
        tempAccessTokenRequest = new TempAccessTokenRequest(
                environment,
                clientId,
                clientSecret);

        //then
        Assert.assertNotNull(clientId);
        Assert.assertNotNull(clientSecret);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);


        Assert.assertNotNull(tempAccessTokenRequest);

        Assert.assertNotNull(tempAccessTokenRequest.getEnvironment());

        Assert.assertEquals(method, tempAccessTokenRequest.getMethod());

        Assert.assertEquals(endpoint, tempAccessTokenRequest.getEndpoint());

        Map<String, Object> body = tempAccessTokenRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 3);
        Assert.assertTrue(body.containsKey("grant_type"));
        Assert.assertTrue(body.containsKey("client_id"));
        Assert.assertTrue(body.containsKey("client_secret"));
        Assert.assertEquals("client_credentials", body.get("grant_type"));
        Assert.assertEquals(clientId, body.get("client_id"));
        Assert.assertEquals(clientSecret, body.get("client_secret"));

        Map<String, String> header = tempAccessTokenRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/x-www-form-urlencoded", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

        Map<String, Object> query = tempAccessTokenRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertTrue(tempAccessTokenRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        tempAccessTokenRequest = null;
    }

}

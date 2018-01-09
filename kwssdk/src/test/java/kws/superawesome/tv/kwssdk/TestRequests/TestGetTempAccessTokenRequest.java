package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

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
    private TempAccessTokenRequest getAppDataRequest;

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
        //given
        String clientId = "__mock_clientId__";
        String clientSecret = "__mock_clientSecret__";
        String endpoint = "oauth/token";
        NetworkMethod method = NetworkMethod.POST;


        //when
        getAppDataRequest = new TempAccessTokenRequest(
                environment,
                clientId,
                clientSecret);


        //then
        Assert.assertNotNull(getAppDataRequest);
        Assert.assertNotNull(getAppDataRequest.getEnvironment());
        Assert.assertEquals(method, getAppDataRequest.getMethod());
        Assert.assertEquals(endpoint, getAppDataRequest.getEndpoint());

        Map<String, Object> body = getAppDataRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 3);
        Assert.assertTrue(body.containsKey("grant_type"));
        Assert.assertTrue(body.containsKey("client_id"));
        Assert.assertTrue(body.containsKey("client_secret"));
        Assert.assertEquals("client_credentials", body.get("grant_type"));
        Assert.assertEquals(clientId, body.get("client_id"));
        Assert.assertEquals(clientSecret, body.get("client_secret"));


        Map<String, String> header = getAppDataRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/x-www-form-urlencoded", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

        Map<String, Object> query = getAppDataRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertTrue(getAppDataRequest.getFormEncodeUrls());


    }

}

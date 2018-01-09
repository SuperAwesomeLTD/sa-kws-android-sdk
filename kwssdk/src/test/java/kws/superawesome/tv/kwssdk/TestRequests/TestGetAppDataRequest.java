package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

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

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);
    }


    @Test
    public final void testRequest() {

        //given
        int appId = 1;
        int userId = 1;
        String endpoint = "v1/apps/" + appId + "/users/" + userId + "/app-data";
        NetworkMethod method = NetworkMethod.GET;
        String token = "__mock_token__";

        //when
        getAppDataRequest = new GetAppDataRequest(
                environment,
                appId,
                userId,
                token
        );

        //then
        Assert.assertNotNull(getAppDataRequest);
        Assert.assertNotNull(getAppDataRequest.getEnvironment());
        Assert.assertEquals(method, getAppDataRequest.getMethod());
        Assert.assertEquals(endpoint, getAppDataRequest.getEndpoint());

        Map<String, Object> body = getAppDataRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = getAppDataRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = getAppDataRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(getAppDataRequest.getFormEncodeUrls());

    }
}

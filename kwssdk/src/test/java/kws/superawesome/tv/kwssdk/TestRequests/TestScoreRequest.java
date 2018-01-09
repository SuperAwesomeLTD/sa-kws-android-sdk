package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.UserScoreRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestScoreRequest {

    //class to be tested
    private UserScoreRequest userScoreRequest;

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
        String endpoint = "v1/apps/" + appId + "/score";
        NetworkMethod method = NetworkMethod.GET;
        String token = "__mock_token__";

        //when

        userScoreRequest = new UserScoreRequest(
                environment,
                appId,
                token
        );


        //then
        Assert.assertNotNull(userScoreRequest);
        Assert.assertNotNull(userScoreRequest.getEnvironment());
        Assert.assertEquals(method, userScoreRequest.getMethod());
        Assert.assertEquals(endpoint, userScoreRequest.getEndpoint());

        Map<String, Object> body = userScoreRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = userScoreRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = userScoreRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(userScoreRequest.getFormEncodeUrls());
    }
}

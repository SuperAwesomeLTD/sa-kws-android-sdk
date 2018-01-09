package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.RandomUsernameRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestRandomUsernameRequest {

    //class to be tested
    private RandomUsernameRequest randomUsernameRequest;

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
        String endpoint = "v2/apps/" + appId + "/random-display-name";
        NetworkMethod method = NetworkMethod.GET;

        //when
        randomUsernameRequest = new RandomUsernameRequest(
                environment,
                appId
        );

        //then
        Assert.assertNotNull(randomUsernameRequest);
        Assert.assertNotNull(randomUsernameRequest.getEnvironment());
        Assert.assertEquals(method, randomUsernameRequest.getMethod());
        Assert.assertEquals(endpoint, randomUsernameRequest.getEndpoint());

        Map<String, Object> body = randomUsernameRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = randomUsernameRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

        Map<String, Object> query = randomUsernameRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(randomUsernameRequest.getFormEncodeUrls());

    }


}

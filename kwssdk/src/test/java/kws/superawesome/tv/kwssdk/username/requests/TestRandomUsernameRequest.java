package kws.superawesome.tv.kwssdk.username.requests;

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

public class TestRandomUsernameRequest {

    //class to be tested
    private RandomUsernameRequest randomUsernameRequest;

    //mocks
    private NetworkEnvironment environment;
    private int appId;
    private String endpoint;
    private NetworkMethod method;

    @Test
    public void test_RandomUsername_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        appId = 1;
        endpoint = "v2/apps/" + appId + "/random-display-name";
        method = NetworkMethod.GET;

        //when
        randomUsernameRequest = new RandomUsernameRequest(
                environment,
                appId
        );

        //then
        Assert.assertTrue(appId > -1);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

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

    @After
    public void unSetup() throws Throwable {
        environment = null;
        randomUsernameRequest = null;
    }
}

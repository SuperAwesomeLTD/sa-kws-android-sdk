package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.username.requests.RandomUsernameRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestRandomUsernameRequest {

    //class to be tested
    private RandomUsernameRequest randomUsernameRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int appId;
    private String endpoint;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        appId = 1;
        endpoint = "v2/apps/" + appId + "/random-display-name";
        method = NetworkMethod.GET;

        //when
        randomUsernameRequest = new RandomUsernameRequest(
                environment,
                appId
        );

    }


    @Test
    public void testConstants() {

        Assert.assertTrue(appId > -1);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }

    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(randomUsernameRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(randomUsernameRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, randomUsernameRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, randomUsernameRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = randomUsernameRequest.getBody();

        Assert.assertNull(body);

    }

    @Test
    public final void testHeader() {
        Map<String, String> header = randomUsernameRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = randomUsernameRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {

        Assert.assertFalse(randomUsernameRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        randomUsernameRequest = null;
    }

}

package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.TriggerEventRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestTriggerEventRequest {


    //class to be tested
    private TriggerEventRequest triggerEventRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int userId, points;
    private String endpoint, token, eventToken;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        userId = 1;
        points = 1;
        endpoint = "v1/users/" + userId + "/trigger-event";
        method = NetworkMethod.POST;
        token = "__mock_token__";
        eventToken = "__mock_eventToken__";

        //when
        triggerEventRequest = new TriggerEventRequest(
                environment,
                points,
                userId,
                token,
                eventToken
        );
    }


    @Test
    public void testConstants() {

        Assert.assertTrue(userId > -1);
        Assert.assertTrue(points > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(eventToken);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }

    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(triggerEventRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(triggerEventRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, triggerEventRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, triggerEventRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = triggerEventRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 2);
        Assert.assertTrue(body.containsKey("points"));
        Assert.assertTrue(body.containsKey("token"));
        Assert.assertEquals(points, body.get("points"));
        Assert.assertEquals(eventToken, body.get("token"));
    }

    @Test
    public final void testHeader() {
        Map<String, String> header = triggerEventRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

    }

    @Test
    public final void testQuery() {

        Map<String, Object> query = triggerEventRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(triggerEventRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        triggerEventRequest = null;
    }

}

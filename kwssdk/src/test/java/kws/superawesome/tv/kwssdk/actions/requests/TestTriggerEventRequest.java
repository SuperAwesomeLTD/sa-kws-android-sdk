package kws.superawesome.tv.kwssdk.actions.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.actions.requests.TriggerEventRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestTriggerEventRequest {


    //class to be tested
    private TriggerEventRequest triggerEventRequest;

    //mocks
    private NetworkEnvironment environment;
    private int userId, points;
    private String endpoint, token, eventId;
    private NetworkMethod method;

    @Test
    public void test_TriggerEvent_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        userId = 1;
        points = 1;
        endpoint = "v1/users/" + userId + "/trigger-event";
        method = NetworkMethod.POST;
        token = "__mock_token__";
        eventId = "__mock_eventID__";

        //when
        triggerEventRequest = new TriggerEventRequest(
                environment,
                eventId,
                points,
                userId,
                token
        );

        //then

        Assert.assertTrue(userId > -1);
        Assert.assertTrue(points > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(eventId);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(triggerEventRequest);

        Assert.assertNotNull(triggerEventRequest.getEnvironment());

        Assert.assertEquals(method, triggerEventRequest.getMethod());
        Assert.assertEquals(endpoint, triggerEventRequest.getEndpoint());

        Map<String, Object> body = triggerEventRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 2);
        Assert.assertTrue(body.containsKey("points"));
        Assert.assertTrue(body.containsKey("token"));
        Assert.assertEquals(points, body.get("points"));
        Assert.assertEquals(eventId, body.get("token"));

        Map<String, String> header = triggerEventRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = triggerEventRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(triggerEventRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        triggerEventRequest = null;
    }

}

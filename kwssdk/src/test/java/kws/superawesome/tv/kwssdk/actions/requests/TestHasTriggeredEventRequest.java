package kws.superawesome.tv.kwssdk.actions.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.actions.requests.HasTriggeredEventRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestHasTriggeredEventRequest {

    //class to be tested
    private HasTriggeredEventRequest hasTriggeredEventRequest;

    //mocks
    private NetworkEnvironment environment;
    private int userId, eventId;
    private NetworkMethod method;
    private String endpoint, token;

    @Test
    public void test_HasTriggeredEvent_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        userId = 1;
        eventId = 1;
        endpoint = "v1/users/" + userId + "/has-triggered-event";
        method = NetworkMethod.POST;
        token = "__mock_token__";


        //when
        hasTriggeredEventRequest = new HasTriggeredEventRequest(
                environment,
                userId,
                eventId,
                token

        );

        //then
        Assert.assertTrue(userId > -1);
        Assert.assertTrue(eventId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(hasTriggeredEventRequest);

        Assert.assertNotNull(hasTriggeredEventRequest.getEnvironment());

        Assert.assertEquals(method, hasTriggeredEventRequest.getMethod());

        Assert.assertEquals(endpoint, hasTriggeredEventRequest.getEndpoint());

        Map<String, Object> body = hasTriggeredEventRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 1);
        Assert.assertTrue(body.containsKey("eventId"));
        Assert.assertEquals(eventId, body.get("eventId"));

        Map<String, String> header = hasTriggeredEventRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = hasTriggeredEventRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(hasTriggeredEventRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        hasTriggeredEventRequest = null;
    }
}

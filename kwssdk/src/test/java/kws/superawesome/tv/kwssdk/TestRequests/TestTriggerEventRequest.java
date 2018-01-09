package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

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

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);
    }


    @Test
    public final void testRequest() {


        //given
        int userId = 1;
        int points = 1;
        String endpoint = "v1/users/" + userId + "/trigger-event";
        NetworkMethod method = NetworkMethod.POST;
        String token = "__mock_token__";
        String eventToken = "__mock_eventToken__";

        //when
        triggerEventRequest = new TriggerEventRequest(
                environment,
                points,
                userId,
                token,
                eventToken
        );

        //then
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
        Assert.assertEquals(eventToken, body.get("token"));

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
}

package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.HasTriggeredEventRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestHasTriggeredEventRequest {


    //class to be tested
    private HasTriggeredEventRequest hasTriggeredEventRequest;

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
        int eventId = 1;
        String endpoint = "v1/users/" + userId + "/has-triggered-event";
        NetworkMethod method = NetworkMethod.POST;
        String token = "__mock_token__";
        
        //when
        hasTriggeredEventRequest = new HasTriggeredEventRequest(
                environment,
                userId,
                eventId,
                token
                
        );
        
        //then
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
    
    
}

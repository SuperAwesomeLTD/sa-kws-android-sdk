package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.UserDetailsRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestUserDetailsRequest {


    //class to be tested
    private UserDetailsRequest userDetailsRequest;

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
        String endpoint = "v1/users/" + userId;
        NetworkMethod method = NetworkMethod.GET;
        String token = "__mock_token__";

        //when
        userDetailsRequest = new UserDetailsRequest(
                environment,
                userId,
                token
        );


        //then
        Assert.assertNotNull(userDetailsRequest);
        Assert.assertNotNull(userDetailsRequest.getEnvironment());
        Assert.assertEquals(method, userDetailsRequest.getMethod());
        Assert.assertEquals(endpoint, userDetailsRequest.getEndpoint());

        Map<String, Object> body = userDetailsRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = userDetailsRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = userDetailsRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(userDetailsRequest.getFormEncodeUrls());
    }
}

package kws.superawesome.tv.kwssdk.user.requests;

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

public class TestGetUserDetailsRequest {

    //class to be tested
    private GetUserDetailsRequest getUserDetailsRequest;

    //mocks
    private NetworkEnvironment environment;
    private int userId;
    private String endpoint, token;
    private NetworkMethod method;

    @Test
    public void test_GetUserDetails_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        userId = 1;
        endpoint = "v1/users/" + userId;
        method = NetworkMethod.GET;
        token = "__mock_token__";

        //when
        getUserDetailsRequest = new GetUserDetailsRequest(
                environment,
                userId,
                token
        );

        //then
        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        //then
        Assert.assertNotNull(getUserDetailsRequest);

        Assert.assertNotNull(getUserDetailsRequest.getEnvironment());

        Assert.assertEquals(method, getUserDetailsRequest.getMethod());

        Assert.assertEquals(endpoint, getUserDetailsRequest.getEndpoint());

        Map<String, Object> body = getUserDetailsRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = getUserDetailsRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = getUserDetailsRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(getUserDetailsRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        getUserDetailsRequest = null;
    }

}

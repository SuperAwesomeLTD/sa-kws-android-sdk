package kws.superawesome.tv.kwssdk.user.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.user.requests.UpdateUserDetailsRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

public class TestUpdateGetUserDetailsRequest {

    //class to be tested
    private UpdateUserDetailsRequest updateUserDetailsRequest;

    //mocks
    private NetworkEnvironment environment;
    private int userId;
    private String endpoint, token;
    private HashMap<String, Object> details;
    private NetworkMethod method;

    @Test
    public void test_UpdateGetUserDetails_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        userId = 1;

        details = new HashMap<>();
        details.put("firstName", "John");
        details.put("lastName", "Droid");

        endpoint = "v1/users/" + userId;
        method = NetworkMethod.PUT;
        token = "__mock_token__";

        //when
        updateUserDetailsRequest = new UpdateUserDetailsRequest(
                environment,
                details,
                userId,
                token);
        //then
        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);
        Assert.assertNotNull(token);

        Assert.assertNotNull(updateUserDetailsRequest);

        Assert.assertNotNull(updateUserDetailsRequest.getEnvironment());

        Assert.assertEquals(method, updateUserDetailsRequest.getMethod());

        Assert.assertEquals(endpoint, updateUserDetailsRequest.getEndpoint());

        Map<String, Object> body = updateUserDetailsRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), details.size());

        Map<String, String> header = updateUserDetailsRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = updateUserDetailsRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(updateUserDetailsRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        updateUserDetailsRequest = null;
    }
}

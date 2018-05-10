package kws.superawesome.tv.kwssdk.actions.requests;

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

public class TestInviteUserRequest {

    //class to be tested
    private InviteUserRequest inviteUserRequest;

    //mocks
    private NetworkEnvironment environment;
    private int userId;
    private String endpoint, token, emailAddress;
    private NetworkMethod method;

    @Test
    public void test_InviteUser_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        userId = 1;
        endpoint = "v1/users/" + userId + "/invite-user";
        method = NetworkMethod.POST;
        token = "__mock_token__";
        emailAddress = "__mock_@_email";


        //when
        inviteUserRequest = new InviteUserRequest(
                environment,
                emailAddress,
                userId,
                token
        );

        //then
        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(inviteUserRequest);

        Assert.assertNotNull(inviteUserRequest.getEnvironment());

        Assert.assertEquals(method, inviteUserRequest.getMethod());

        Assert.assertEquals(endpoint, inviteUserRequest.getEndpoint());

        Map<String, Object> body = inviteUserRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 1);
        Assert.assertTrue(body.containsKey("email"));
        Assert.assertEquals(emailAddress, body.get("email"));

        Map<String, String> header = inviteUserRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = inviteUserRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(inviteUserRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        inviteUserRequest = null;
    }
}

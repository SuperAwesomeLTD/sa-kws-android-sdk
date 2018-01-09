package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.InviteUserRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestInviteUserRequest {

    //class to be tested
    private InviteUserRequest inviteUserRequest;

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
        String endpoint = "v1/users/" + userId + "/invite-user";
        NetworkMethod method = NetworkMethod.POST;
        String token = "__mock_token__";
        String emailAddress = "__mock_@_email";

        //when
        inviteUserRequest = new InviteUserRequest(
                environment,
                emailAddress,
                userId,
                token
        );

        //then
        //then
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

}

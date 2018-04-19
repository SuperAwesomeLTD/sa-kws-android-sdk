package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.invite_user.InviteUserRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestInviteUserRequest {

    //class to be tested
    private InviteUserRequest inviteUserRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int userId;
    private String endpoint, token, emailAddress;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

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

    }


    @Test
    public void testConstants() {

        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }

    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(inviteUserRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(inviteUserRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, inviteUserRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, inviteUserRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = inviteUserRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 1);
        Assert.assertTrue(body.containsKey("email"));
        Assert.assertEquals(emailAddress, body.get("email"));


    }

    @Test
    public final void testHeader() {
        Map<String, String> header = inviteUserRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = inviteUserRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(inviteUserRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        inviteUserRequest = null;
    }


}

package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestLoginRequest {


    //class to be tested
    private LoginUserRequest loginUserRequest;

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
        String username = "__mock_username__";
        String password = "__mock_password__";
        String clientId = "__mock_clientId__";
        String clientSecret = "__mock_clientSecret__";
        String endpoint = "oauth/token";
        NetworkMethod method = NetworkMethod.POST;


        //when
        loginUserRequest = new LoginUserRequest(
                environment,
                username,
                password,
                clientId,
                clientSecret
        );

        //then
        Assert.assertNotNull(loginUserRequest);
        Assert.assertNotNull(loginUserRequest.getEnvironment());
        Assert.assertEquals(method, loginUserRequest.getMethod());
        Assert.assertEquals(endpoint, loginUserRequest.getEndpoint());

        Map<String, Object> body = loginUserRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 5);
        Assert.assertTrue(body.containsKey("grant_type"));
        Assert.assertTrue(body.containsKey("username"));
        Assert.assertTrue(body.containsKey("password"));
        Assert.assertTrue(body.containsKey("client_id"));
        Assert.assertTrue(body.containsKey("client_secret"));

        Assert.assertEquals("password", body.get("grant_type"));
        Assert.assertEquals(username, body.get("username"));
        Assert.assertEquals(password, body.get("password"));
        Assert.assertEquals(clientId, body.get("client_id"));
        Assert.assertEquals(clientSecret, body.get("client_secret"));


        Map<String, String> header = loginUserRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/x-www-form-urlencoded", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

        Map<String, Object> query = loginUserRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertTrue(loginUserRequest.getFormEncodeUrls());


    }
}

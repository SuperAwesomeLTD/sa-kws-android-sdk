package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.After;
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
    private String username, password, clientId, clientSecret, endpoint;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        username = "__mock_username__";
        password = "__mock_password__";
        clientId = "__mock_clientId__";
        clientSecret = "__mock_clientSecret__";
        endpoint = "oauth/token";
        method = NetworkMethod.POST;

        //when
        loginUserRequest = new LoginUserRequest(
                environment,
                username,
                password,
                clientId,
                clientSecret
        );


    }

    @Test
    public void testConstants() {

        Assert.assertNotNull(username);
        Assert.assertNotNull(password);
        Assert.assertNotNull(clientId);
        Assert.assertNotNull(clientSecret);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }


    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(loginUserRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(loginUserRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, loginUserRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, loginUserRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
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

    }

    @Test
    public final void testHeader() {
        Map<String, String> header = loginUserRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/x-www-form-urlencoded", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = loginUserRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertTrue(loginUserRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        loginUserRequest = null;
    }


}

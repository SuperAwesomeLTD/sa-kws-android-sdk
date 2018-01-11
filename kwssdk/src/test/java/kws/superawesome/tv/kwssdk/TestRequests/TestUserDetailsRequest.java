package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.After;
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
    private int userId;
    private String endpoint, token;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        userId = 1;
        endpoint = "v1/users/" + userId;
        method = NetworkMethod.GET;
        token = "__mock_token__";

        //when
        userDetailsRequest = new UserDetailsRequest(
                environment,
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
        Assert.assertNotNull(userDetailsRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(userDetailsRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, userDetailsRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, userDetailsRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = userDetailsRequest.getBody();

        Assert.assertNull(body);
    }

    @Test
    public final void testHeader() {
        Map<String, String> header = userDetailsRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

    }

    @Test
    public final void testQuery() {

        Map<String, Object> query = userDetailsRequest.getQuery();

        Assert.assertNull(query);

    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(userDetailsRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        userDetailsRequest = null;
    }

}

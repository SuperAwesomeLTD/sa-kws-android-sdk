package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.user.requests.UpdateUserDetailsRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

public class TestUpdateGetUserDetailsRequest {


    //class to be tested
    private UpdateUserDetailsRequest updateUserDetailsRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int userId;
    private String endpoint, token;
    private HashMap<String, Object> details;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);


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

    }


    @Test
    public void testConstants() {

        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);
        Assert.assertNotNull(token);
    }


    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(updateUserDetailsRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(updateUserDetailsRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, updateUserDetailsRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, updateUserDetailsRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = updateUserDetailsRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), details.size());
    }

    @Test
    public final void testHeader() {
        Map<String, String> header = updateUserDetailsRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));
    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = updateUserDetailsRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(updateUserDetailsRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        updateUserDetailsRequest = null;
    }
}

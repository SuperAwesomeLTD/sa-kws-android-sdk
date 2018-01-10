package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.CreateUserRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;


/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestCreateUserRequest {

    //class to be tested
    private CreateUserRequest createUserRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private String username, password, dateOfBirth, country, parentEmail, token, endpoint;
    private int appID;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        username = "__mock_username__";
        password = "__mock_password__";
        dateOfBirth = "__mock_dob__";
        country = "__mock_country__";
        parentEmail = "__mock_@_email__";
        appID = 1;
        token = "__mock_token__";
        endpoint = "v1/apps/" + appID + "/users";
        method = NetworkMethod.POST;

        setRequestConstruct();

    }

    @Test
    public void testConstants() {

        Assert.assertNotNull(username);
        Assert.assertNotNull(password);
        Assert.assertNotNull(dateOfBirth);
        Assert.assertNotNull(country);
        Assert.assertNotNull(parentEmail);
        Assert.assertTrue(appID > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        setRequestConstruct();
    }

    public void setRequestConstruct() {
        //when
        createUserRequest = new CreateUserRequest(
                environment,
                username,
                password,
                dateOfBirth,
                country,
                parentEmail,
                appID,
                token
        );
    }


    @Test
    public final void testRequest() {
        Assert.assertNotNull(createUserRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(createUserRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, createUserRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, createUserRequest.getEndpoint());
    }


    @Test
    public final void testBody() {

        Map<String, Object> body = createUserRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 6);
        Assert.assertTrue(body.containsKey("username"));
        Assert.assertTrue(body.containsKey("password"));
        Assert.assertTrue(body.containsKey("dateOfBirth"));
        Assert.assertTrue(body.containsKey("country"));
        Assert.assertTrue(body.containsKey("parentEmail"));
        Assert.assertTrue(body.containsKey("authenticate"));
        Assert.assertEquals(username, body.get("username"));
        Assert.assertEquals(password, body.get("password"));
        Assert.assertEquals(dateOfBirth, body.get("dateOfBirth"));
        Assert.assertEquals(country, body.get("country"));
        Assert.assertEquals(parentEmail, body.get("parentEmail"));
        Assert.assertEquals(true, body.get("authenticate"));

    }

    @Test
    public final void testHeader() {
        Map<String, String> header = createUserRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));
    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = createUserRequest.getQuery();

        Assert.assertNotNull(query);
        Assert.assertEquals(query.size(), 1);
        Assert.assertTrue(query.containsKey("access_token"));
        Assert.assertEquals(token, query.get("access_token"));
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(createUserRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        createUserRequest = null;
    }


}

package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

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
        String dateOfBirth = "__mock_dob__";
        String country = "__mock_country__";
        String parentEmail = "__mock_@_token__";
        int appID = 2;
        String token = "__mock_token__";
        String endpoint = "v1/apps/" + appID + "/users";
        NetworkMethod method = NetworkMethod.POST;


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


        //then
        Assert.assertNotNull(createUserRequest);
        Assert.assertNotNull(createUserRequest.getEnvironment());
        Assert.assertEquals(method, createUserRequest.getMethod());
        Assert.assertEquals(endpoint, createUserRequest.getEndpoint());

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

        Map<String, String> header = createUserRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));


        Map<String, Object> query = createUserRequest.getQuery();

        Assert.assertNotNull(query);
        Assert.assertEquals(query.size(), 1);
        Assert.assertTrue(query.containsKey("access_token"));
        Assert.assertEquals(token, query.get("access_token"));
    }


}

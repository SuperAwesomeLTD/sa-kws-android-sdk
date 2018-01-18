package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.PermissionsRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestPermissionsRequest {


    //class to be tested
    private PermissionsRequest permissionsRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int userId;
    private String endpoint,token;
    private NetworkMethod method;
    private List<String> permissionsList;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        userId = 1;
        endpoint = "v1/users/" + userId + "/request-permissions";
        method = NetworkMethod.POST;
        token = "__mock_token__";

        permissionsList = new ArrayList<>();
        permissionsList.add("__mock_permission_1__");
        permissionsList.add("__mock_permission_2__");


        //when
        permissionsRequest = new PermissionsRequest(
                environment,
                userId,
                token,
                permissionsList
        );


    }

    @Test
    public void testConstants() {

        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(permissionsList);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }



    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(permissionsRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(permissionsRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, permissionsRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, permissionsRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = permissionsRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 1);
        Assert.assertTrue(body.containsKey("permissions"));
        Assert.assertEquals(new JSONArray(permissionsList).toString(), body.get("permissions").toString());

    }

    @Test
    public final void testHeader() {
        Map<String, String> header = permissionsRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = permissionsRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(permissionsRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        permissionsRequest = null;
    }


}

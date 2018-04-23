package kws.superawesome.tv.kwssdk.actions.requests;

import junit.framework.Assert;

import org.json.JSONArray;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.actions.requests.PermissionsRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestPermissionsRequest {

    //class to be tested
    private PermissionsRequest permissionsRequest;

    //mocks
    private NetworkEnvironment environment;
    private int userId;
    private String endpoint, token;
    private NetworkMethod method;
    private List<String> permissionsList;

    @Test
    public void test_Permissions_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

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

        //then
        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(permissionsList);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(permissionsRequest);

        Assert.assertNotNull(permissionsRequest.getEnvironment());

        Assert.assertEquals(method, permissionsRequest.getMethod());

        Assert.assertEquals(endpoint, permissionsRequest.getEndpoint());

        Map<String, Object> body = permissionsRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 1);
        Assert.assertTrue(body.containsKey("permissions"));
        Assert.assertEquals(new JSONArray(permissionsList).toString(), body.get("permissions").toString());

        Map<String, String> header = permissionsRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = permissionsRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(permissionsRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        permissionsRequest = null;
    }
}

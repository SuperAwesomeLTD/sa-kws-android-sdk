package kws.superawesome.tv.kwssdk.actions.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.NetworkEnvironment;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestGetAppDataRequest {

    //class to be tested
    private GetAppDataRequest getAppDataRequest;

    //mocks
    private NetworkEnvironment environment;
    private String token, endpoint;
    private int appId, userId;
    private NetworkMethod method;

    @Test
    public void test_GetAppData_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        appId = 1;
        userId = 1;
        endpoint = "v1/apps/" + appId + "/users/" + userId + "/app-data";
        method = NetworkMethod.GET;
        token = "__mock_token__";

        //when
        getAppDataRequest = new GetAppDataRequest(
                environment,
                appId,
                userId,
                token
        );

        //then
        Assert.assertTrue(appId > -1);
        Assert.assertTrue(userId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);

        Assert.assertNotNull(getAppDataRequest);

        Assert.assertNotNull(getAppDataRequest.getEnvironment());

        Assert.assertEquals(method, getAppDataRequest.getMethod());

        Assert.assertEquals(endpoint, getAppDataRequest.getEndpoint());

        Map<String, Object> body = getAppDataRequest.getBody();
        Assert.assertNull(body);

        Map<String, String> header = getAppDataRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = getAppDataRequest.getQuery();
        Assert.assertNull(query);

        Assert.assertFalse(getAppDataRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        getAppDataRequest = null;
    }
}

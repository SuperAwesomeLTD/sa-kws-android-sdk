package kws.superawesome.tv.kwssdk.app.data.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.app.data.requests.SetAppDataRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestSetAppDataRequest {


    //class to be tested
    private SetAppDataRequest setAppDataRequest;

    //mocks
    private NetworkEnvironment environment;
    private int appId, userId, value;
    private String key, endpoint, token;
    private NetworkMethod method;

    @Test
    public void test_SetAppData_Request_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);


        //given
        appId = 1;
        userId = 1;
        key = "__mock_nameValue__";
        value = 1;
        endpoint = "v1/apps/" + appId + "/users/" + userId + "/app-data/set";
        method = NetworkMethod.POST;
        token = "__mock_token__";

        //when
        setAppDataRequest = new SetAppDataRequest(
                environment,
                appId,
                userId,
                value,
                key,
                token
        );

        //then
        Assert.assertTrue(appId > -1);
        Assert.assertTrue(userId > -1);
        Assert.assertTrue(value > -1);
        Assert.assertNotNull(key);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);
        Assert.assertNotNull(token);

        Assert.assertNotNull(setAppDataRequest);

        Assert.assertNotNull(setAppDataRequest.getEnvironment());

        Assert.assertEquals(method, setAppDataRequest.getMethod());

        Assert.assertEquals(endpoint, setAppDataRequest.getEndpoint());

        Map<String, Object> body = setAppDataRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 2);
        Assert.assertTrue(body.containsKey("name"));
        Assert.assertTrue(body.containsKey("value"));
        Assert.assertEquals(key, body.get("name"));
        Assert.assertEquals(value, body.get("value"));

        Map<String, String> header = setAppDataRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = setAppDataRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(setAppDataRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        setAppDataRequest = null;
    }
}

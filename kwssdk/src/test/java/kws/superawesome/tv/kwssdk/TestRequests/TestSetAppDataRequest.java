package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.SetAppDataRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestSetAppDataRequest {


    //class to be tested
    private SetAppDataRequest setAppDataRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int appId, userId, numericValue;
    private String nameValue, endpoint, token;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);


        //given
        appId = 1;
        userId = 1;
        nameValue = "__mock_nameValue__";
        numericValue = 1;
        endpoint = "v1/apps/" + appId + "/users/" + userId + "/app-data/set";
        method = NetworkMethod.POST;
        token = "__mock_token__";

        setRequestConstruct();

    }


    @Test
    public void testConstants() {

        Assert.assertTrue(appId > -1);
        Assert.assertTrue(userId > -1);
        Assert.assertTrue(numericValue > -1);
        Assert.assertNotNull(nameValue);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);
        Assert.assertNotNull(token);

        setRequestConstruct();
    }

    public void setRequestConstruct() {
        //when
        setAppDataRequest = new SetAppDataRequest(
                environment,
                appId,
                userId,
                nameValue,
                numericValue,
                token
        );
    }


    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(setAppDataRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(setAppDataRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, setAppDataRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, setAppDataRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = setAppDataRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 2);
        Assert.assertTrue(body.containsKey("name"));
        Assert.assertTrue(body.containsKey("value"));
        Assert.assertEquals(nameValue, body.get("name"));
        Assert.assertEquals(numericValue, body.get("value"));

    }

    @Test
    public final void testHeader() {
        Map<String, String> header = setAppDataRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));
    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = setAppDataRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(setAppDataRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        setAppDataRequest = null;
    }
}

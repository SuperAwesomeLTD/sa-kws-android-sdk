package kws.superawesome.tv.kwssdk.TestRequests;

import junit.framework.Assert;

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

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);
    }


    @Test
    public final void testRequest() {

        //given
        int appId = 1;
        int userId = 1;
        String nameValue = "__mock_nameValue__";
        int numericValue = 1;
        String endpoint = "v1/apps/" + appId + "/users/" + userId + "/app-data/set";
        NetworkMethod method = NetworkMethod.POST;
        String token = "__mock_token__";

        //when

        setAppDataRequest = new SetAppDataRequest(
                environment,
                appId,
                userId,
                nameValue,
                numericValue,
                token
        );


        //then
        Assert.assertNotNull(setAppDataRequest);
        Assert.assertNotNull(setAppDataRequest.getEnvironment());
        Assert.assertEquals(method, setAppDataRequest.getMethod());
        Assert.assertEquals(endpoint, setAppDataRequest.getEndpoint());

        Map<String, Object> body = setAppDataRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 2);
        Assert.assertTrue(body.containsKey("name"));
        Assert.assertTrue(body.containsKey("value"));
        Assert.assertEquals(nameValue, body.get("name"));
        Assert.assertEquals(numericValue, body.get("value"));

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
}

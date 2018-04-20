package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.scoring.requests.LeadersRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestLeadersRequest {

    //class to be tested
    private LeadersRequest leadersRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int appId;
    private String endpoint, token;
    private NetworkMethod method;

    @Test
    public void test_Leaders_Request_Init() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        appId = 1;
        endpoint = "v1/apps/" + appId + "/leaders";
        method = NetworkMethod.GET;
        token = "__mock_token__";

        //when
        leadersRequest = new LeadersRequest(
                environment,
                appId,
                token
        );

        //then
        Assert.assertTrue(appId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(leadersRequest);

        Assert.assertNotNull(leadersRequest.getEnvironment());

        Assert.assertEquals(method, leadersRequest.getMethod());

        Assert.assertEquals(endpoint, leadersRequest.getEndpoint());

        Map<String, Object> body = leadersRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = leadersRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = leadersRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(leadersRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        leadersRequest = null;
    }
}

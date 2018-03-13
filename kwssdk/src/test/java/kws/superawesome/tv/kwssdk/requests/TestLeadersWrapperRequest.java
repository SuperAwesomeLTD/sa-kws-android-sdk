package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.LeadersRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestLeadersWrapperRequest {

    //class to be tested
    private LeadersRequest leadersRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private int appId;
    private String endpoint, token;
    private NetworkMethod method;

    @Before
    public void setup() {
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

    }

    @Test
    public void testConstants() {

        Assert.assertTrue(appId > -1);
        Assert.assertNotNull(token);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }

    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(leadersRequest);


    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(leadersRequest.getEnvironment());

    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, leadersRequest.getMethod());

    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, leadersRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = leadersRequest.getBody();

        Assert.assertNull(body);
    }

    @Test
    public final void testHeader() {
        Map<String, String> header = leadersRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = leadersRequest.getQuery();

        Assert.assertNull(query);

    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertFalse(leadersRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        leadersRequest = null;
    }



}

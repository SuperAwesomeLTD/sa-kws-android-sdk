package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.OAuthUserTokenRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 26/01/2018.
 */

public class TestOAuthUserTokenRequest {

    //class to be tested

    private OAuthUserTokenRequest oAuthUserTokenRequest;

    //mocks
    private KWSNetworkEnvironment environment;
    private String clientId, authCode, codeVerifier, clientSecret, endpoint;
    private NetworkMethod method;

    @Before
    public void setup() {
        // setup mocks
        environment = Mockito.mock(KWSNetworkEnvironment.class);

        //given
        clientId = "__mock_clientId__";
        authCode = "__mock_auth_code__";
        codeVerifier = "__mock_code_verifier__";
        clientSecret = "__mock_clientSecret__";
        endpoint = "oauth/token";
        method = NetworkMethod.POST;

        //when
        oAuthUserTokenRequest = new OAuthUserTokenRequest(
                environment,
                clientId,
                authCode,
                codeVerifier,
                clientSecret
        );


    }

    @Test
    public void testConstants() {

        Assert.assertNotNull(clientId);
        Assert.assertNotNull(authCode);
        Assert.assertNotNull(codeVerifier);
        Assert.assertNotNull(clientSecret);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

    }


    @Test
    public final void testRequest() {
        //then
        Assert.assertNotNull(oAuthUserTokenRequest);
    }


    @Test
    public final void testRequestEnvironment() {
        Assert.assertNotNull(oAuthUserTokenRequest.getEnvironment());
    }

    @Test
    public final void testMethod() {
        Assert.assertEquals(method, oAuthUserTokenRequest.getMethod());
    }


    @Test
    public final void testEndpoint() {
        Assert.assertEquals(endpoint, oAuthUserTokenRequest.getEndpoint());
    }


    @Test
    public final void testBody() {
        Map<String, Object> body = oAuthUserTokenRequest.getBody();

        Assert.assertNotNull(body);
        Assert.assertEquals(body.size(), 5);
        Assert.assertTrue(body.containsKey("grant_type"));
        Assert.assertTrue(body.containsKey("client_id"));
        Assert.assertTrue(body.containsKey("code"));
        Assert.assertTrue(body.containsKey("client_secret"));
        Assert.assertTrue(body.containsKey("code_verifier"));

        Assert.assertEquals("authorization_code", body.get("grant_type"));
        Assert.assertEquals(clientId, body.get("client_id"));
        Assert.assertEquals(authCode, body.get("code"));
        Assert.assertEquals(clientSecret, body.get("client_secret"));
        Assert.assertEquals(codeVerifier, body.get("code_verifier"));

    }

    @Test
    public final void testHeader() {
        Map<String, String> header = oAuthUserTokenRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/x-www-form-urlencoded", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

    }

    @Test
    public final void testQuery() {
        Map<String, Object> query = oAuthUserTokenRequest.getQuery();

        Assert.assertNull(query);
    }

    @Test
    public final void testFormEncodedURLs() {
        Assert.assertTrue(oAuthUserTokenRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        oAuthUserTokenRequest = null;
    }
}

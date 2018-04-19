package kws.superawesome.tv.kwssdk.requests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.authentication.requests.OAuthUserTokenRequest;
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

    @Test
    public void test_OAuthUserToken_Request_Init() {
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

        //then
        Assert.assertNotNull(clientId);
        Assert.assertNotNull(authCode);
        Assert.assertNotNull(codeVerifier);
        Assert.assertNotNull(clientSecret);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(oAuthUserTokenRequest);

        Assert.assertNotNull(oAuthUserTokenRequest.getEnvironment());

        Assert.assertEquals(method, oAuthUserTokenRequest.getMethod());

        Assert.assertEquals(endpoint, oAuthUserTokenRequest.getEndpoint());

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

        Map<String, String> header = oAuthUserTokenRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 1);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/x-www-form-urlencoded", header.get("Content-Type"));
        Assert.assertFalse(header.containsKey("Authorization"));

        Map<String, Object> query = oAuthUserTokenRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertTrue(oAuthUserTokenRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        oAuthUserTokenRequest = null;
    }
}

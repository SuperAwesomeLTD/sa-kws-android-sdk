package kws.superawesome.tv.kwssdk.scoring.requests;

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

public class TestScoreRequest {

    //class to be tested
    private ScoreRequest scoreRequest;

    //mocks
    private NetworkEnvironment environment;
    private int appId;
    private String endpoint, token;
    private NetworkMethod method;

    @Test
    public void test_ScoreRequest_Init() {
        // setup mocks
        environment = Mockito.mock(NetworkEnvironment.class);

        //given
        appId = 1;
        endpoint = "v1/apps/" + appId + "/score";
        method = NetworkMethod.GET;
        token = "__mock_token__";

        //when
        scoreRequest = new ScoreRequest(
                environment,
                appId,
                token
        );

        //then
        Assert.assertTrue(appId > -1);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(method);

        Assert.assertNotNull(scoreRequest);

        Assert.assertNotNull(scoreRequest.getEnvironment());

        Assert.assertEquals(method, scoreRequest.getMethod());

        Assert.assertEquals(endpoint, scoreRequest.getEndpoint());

        Map<String, Object> body = scoreRequest.getBody();

        Assert.assertNull(body);

        Map<String, String> header = scoreRequest.getHeaders();

        Assert.assertNotNull(header);
        Assert.assertEquals(header.size(), 2);
        Assert.assertTrue(header.containsKey("Content-Type"));
        Assert.assertEquals("application/json", header.get("Content-Type"));
        Assert.assertTrue(header.containsKey("Authorization"));
        Assert.assertEquals("Bearer " + token, header.get("Authorization"));

        Map<String, Object> query = scoreRequest.getQuery();

        Assert.assertNull(query);

        Assert.assertFalse(scoreRequest.getFormEncodeUrls());
    }

    @After
    public void unSetup() throws Throwable {
        environment = null;
        scoreRequest = null;
    }

}

package kws.superawesome.tv.kwssdk.TestRequests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.requests.GetAppDataRequest;
import tv.superawesome.samobilebase.network.NetworkMethod;

/**
 * Created by guilherme.mota on 09/01/2018.
 */

public class TestAppDataRequest {


    //class to be tested
    private GetAppDataRequest getAppDataRequest;

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
        int appId = 2;
        int userId = 25;
        String endpoint = "v1/apps/" + appId + "/users/" + userId + "/app-data";
        NetworkMethod method = NetworkMethod.GET;
        String token = "__mock_token__";

        //when


        //then


    }
}

package kws.superawesome.tv.kwssdk.scoring.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.TestBaseService;
import kws.superawesome.tv.kwssdk.base.ComplianceSDK;
import tv.superawesome.protobufs.score.services.IScoringService;

/**
 * Created by guilherme.mota on 19/03/2018.
 */

public class TestScoringService extends TestBaseService {

    // class to test
    protected IScoringService service;

    //given
    protected int goodAppId = 2;
    protected int badAppId = 0;

    protected String goodMockedToken = "good_token";

    @Override
    @Before
    public void setup() throws Throwable {

        //extended method from Base
        super.setup();


        //then
        // init class to test
        ComplianceSDK sdk = new ComplianceSDK(environment, task);
        service = sdk.get(IScoringService.class);

    }

    @Test
    public void test_UserService_ToNot_BeNull() {
        Assert.assertNotNull(service);
    }


    @After
    public void shutdown() {
        service = null;
    }
}

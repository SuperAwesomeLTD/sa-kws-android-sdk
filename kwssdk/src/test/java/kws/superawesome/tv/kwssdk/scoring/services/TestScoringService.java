package kws.superawesome.tv.kwssdk.scoring.services;

import org.junit.Before;

import kws.superawesome.tv.kwssdk.base.ComplianceSDK;
import kws.superawesome.tv.kwssdk.TestBaseService;
import tv.superawesome.protobufs.features.scoring.IScoringService;

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
        ComplianceSDK factory = new ComplianceSDK(environment, task);
        service = factory.get(IScoringService.class);

    }

}

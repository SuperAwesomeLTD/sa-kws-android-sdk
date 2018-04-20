package kws.superawesome.tv.kwssdk.user.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.base.ComplianceSDK;
import kws.superawesome.tv.kwssdk.TestBaseService;
import tv.superawesome.protobufs.features.user.IUserService;

/**
 * Created by guilherme.mota on 16/01/2018.
 */

public class TestUserService extends TestBaseService {

    // class to test
    protected IUserService service;

    //given
    protected int goodAppId = 2;
    protected int badAppId = 0;

    protected int goodUserId = 25;
    protected int badUserId = 0;

    protected String goodMockedToken = "good_token";

    @Override
    @Before
    public void setup() throws Throwable {

        //extended method from Base
        super.setup();


        //then
        // init class to test
        // init class to test
        ComplianceSDK factory = new ComplianceSDK(environment, task);
        service = factory.get(IUserService.class);


    }

    @Test
    public void test_UserService_ToNot_BeNull() {
        Assert.assertNotNull(service);
    }


    @After
    public void shutdown(){
        service = null;
    }

}

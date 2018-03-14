package kws.superawesome.tv.kwssdk.services.odlServices.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.services.AppService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestAppService extends TestBaseService {

    //class to test
    protected AppService service;

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


        //when
        // init class to test
        service = KWSSDK.get(environment, AppService.class, task);


    }


    @Test
    public void testServiceToNotBeNull() {
        Assert.assertNotNull(service);
    }

}

package kws.superawesome.tv.kwssdk.services.app;

import org.junit.Before;

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

    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //when
        // init class to test
        service = KWSSDK.get(environment, AppService.class, task);


    }

}

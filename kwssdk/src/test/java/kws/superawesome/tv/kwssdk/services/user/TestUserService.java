package kws.superawesome.tv.kwssdk.services.user;

import org.junit.Before;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.services.UserService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;

/**
 * Created by guilherme.mota on 16/01/2018.
 */

public class TestUserService extends TestBaseService {

    // class to test
    protected UserService service;

    //given
    protected int goodAppId = 2;
    protected int badAppId = 0;

    protected int goodUserId = 25;
    protected int badUserId = 0;

    protected String goodMockedToken = "good_token";
    protected String goodEmail = "good_email";

    protected String badEmail = "bad_email";

    protected String goodPermissionString = "good_permission";
    protected String badPermissionString = "bad_permission";


    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //then
        // init class to test
        service = KWSSDK.get(environment, UserService.class, task);


    }

}

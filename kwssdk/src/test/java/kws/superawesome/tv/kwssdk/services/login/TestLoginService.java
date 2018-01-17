package kws.superawesome.tv.kwssdk.services.login;

import org.junit.Before;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.services.LoginService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class TestLoginService extends TestBaseService {

    // class to test
    protected LoginService service;

    //given
    protected String goodUsername = "good_username";
    protected String badUsername = "bad_username";

    protected String goodPassword = "good_password";
    protected String badPassword = "bad_password";

    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //then
        // init class to test
        service = KWSSDK.get(environment, LoginService.class, task);
    }


}

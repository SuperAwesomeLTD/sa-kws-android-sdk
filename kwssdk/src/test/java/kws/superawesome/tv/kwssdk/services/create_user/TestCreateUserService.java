package kws.superawesome.tv.kwssdk.services.create_user;

import org.junit.Before;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.services.CreateUserService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestCreateUserService extends TestBaseService {

    //class to test
    protected CreateUserService service;

    //given
    protected String goodMockedToken = "good_token";
    protected String badMockedToken = "bad_token";

    protected int goodAppId = 2;
    protected int badAppId = 0;


    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //when
        // init class to test
        service = KWSSDK.get(environment, CreateUserService.class, task);


    }

}

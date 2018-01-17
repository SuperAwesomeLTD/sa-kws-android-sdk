package kws.superawesome.tv.kwssdk.services.random_username;

import org.junit.Before;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.services.RandomUsernameService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;

/**
 * Created by guilherme.mota on 16/01/2018.
 */

public class TestRandomUsernameService extends TestBaseService {


    //class to test
    protected RandomUsernameService service;


    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //then
        // init class to test
        service = KWSSDK.get(environment, RandomUsernameService.class, task);


    }

}

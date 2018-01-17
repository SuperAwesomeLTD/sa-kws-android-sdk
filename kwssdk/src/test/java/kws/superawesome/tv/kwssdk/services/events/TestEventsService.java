package kws.superawesome.tv.kwssdk.services.events;

import org.junit.Before;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.services.EventsService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestEventsService extends TestBaseService {

    //class to test
    protected EventsService service;

    //given
    protected int goodUserId = 25;
    protected int badUserId = 0;

    protected String goodMockedToken = "good_token";


    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //when
        // init class to test
        service = KWSSDK.get(environment, EventsService.class, task);

    }

}
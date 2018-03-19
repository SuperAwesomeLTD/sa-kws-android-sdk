package kws.superawesome.tv.kwssdk.services.useractions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.services.TestBaseService;
import tv.superawesome.protobufs.features.user.IUserActionsService;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestUserActionsService extends TestBaseService {

    //class to test
    protected IUserActionsService service;

    protected int goodAppId = 2;
    protected int badAppId = 0;

    protected int goodUserId = 25;
    protected int badUserId = 0;

    protected String goodMockedToken = "good_token";

    protected String goodEmail = "good_email";

    protected String badEmail = "bad_email";

    protected String goodPermissionString = "good_permission";
    protected String badPermissionString = "bad_permission";

    @Override
    @Before
    public void setup() throws Throwable {

        //extended method from Base
        super.setup();


        //when
        // init class to test
        // init class to test
        KWSSDK factory = new KWSSDK(environment, task);
        service = factory.get(IUserActionsService.class);


    }


    @Test
    public void testServiceToNotBeNull() {
        Assert.assertNotNull(service);
    }

}

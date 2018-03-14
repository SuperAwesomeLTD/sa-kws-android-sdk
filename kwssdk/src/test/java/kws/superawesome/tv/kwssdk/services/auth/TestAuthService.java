package kws.superawesome.tv.kwssdk.services.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.services.LoginService;
import kws.superawesome.tv.kwssdk.services.TestBaseService;
import tv.superawesome.protobufs.features.auth.IAuthService;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class TestAuthService extends TestBaseService {

    // class to test
    protected IAuthService service;

    //given
    protected String goodUsername = "good_username";
    protected String badUsername = "bad_username";

    protected String goodPassword = "good_password";
    protected String badPassword = "bad_password";

    @Override
    @Before
    public void setup() throws Throwable {

        //extended method from Base
        super.setup();


        //then
        // init class to test
        KWSSDK factory = new KWSSDK(environment);
        service = factory.getService(IAuthService.class);

    }


    @Test
    public void testServiceToNotBeNull() {
        Assert.assertNotNull(service);
    }

}

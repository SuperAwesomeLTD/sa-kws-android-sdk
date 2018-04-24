package kws.superawesome.tv.kwssdk.authentication.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.TestBaseService;
import kws.superawesome.tv.kwssdk.base.ComplianceSDK;
import tv.superawesome.protobufs.authentication.services.IAuthService;

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


        //when
        // init class to test
        ComplianceSDK sdk = new ComplianceSDK(environment, task);
        service = sdk.get(IAuthService.class);

    }


    @Test
    public void testService_ToNot_BeNull() {
        Assert.assertNotNull(service);
    }

}

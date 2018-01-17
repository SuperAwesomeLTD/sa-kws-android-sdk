package kws.superawesome.tv.kwssdk.services.user;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.responses.UserDetails;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserService_GetUserDetails extends TestUserService {


    //for User Details

    @Test
    public void testUserServiceGetUserDetailsOK() {

        service.getUserDetails(goodUserId, goodMockedToken, new Function2<UserDetails, Throwable, Unit>() {
            @Override
            public Unit invoke(UserDetails userDetails, Throwable throwable) {

                Assert.assertNotNull(userDetails);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceGetUserDetailsBadUserId() {

        service.getUserDetails(badUserId, goodMockedToken, new Function2<UserDetails, Throwable, Unit>() {
            @Override
            public Unit invoke(UserDetails userDetails, Throwable throwable) {

                Assert.assertNull(userDetails);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

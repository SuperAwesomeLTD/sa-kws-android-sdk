package kws.superawesome.tv.kwssdk.services.user;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class TestUserService_UpdateUserDetails extends TestUserService {

    private HashMap<String, Object> details;

    @Test
    public void testUserServiceGetUserDetailsBadUserId() {

        //TODO needs fixing when protobufs update

       /** service.updateUser(details, badUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });*/

    }

    //TODO rest of the tests...

}

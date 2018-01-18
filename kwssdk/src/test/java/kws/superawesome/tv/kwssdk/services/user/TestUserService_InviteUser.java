package kws.superawesome.tv.kwssdk.services.user;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserService_InviteUser extends TestUserService {


    //for Invite User
    @Test
    public void testUserServiceInviteUserOK() {

        service.inviteUser(goodEmail, goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertTrue(aSuccess);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceInviteUserBadEmptyEmail() {
        service.inviteUser("", goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserServiceInviteUserBadNullEmail() {
        service.inviteUser(null, goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testUserServiceInviteUserBadEmail() {

        service.inviteUser(badEmail, goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceInviteUserBadUserId() {

        service.inviteUser(goodEmail, badUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }
}

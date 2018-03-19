package kws.superawesome.tv.kwssdk.services.useractions;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserActionsService_InviteUser extends TestUserActionsService {


    //for Invite User
    @Test
    public void testUserServiceInviteUserOK() {

        service.inviteUser(goodEmail, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceInviteUserBadEmptyEmail() {
        service.inviteUser("", goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserServiceInviteUserBadNullEmail() {
        service.inviteUser(null, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testUserServiceInviteUserBadEmail() {

        service.inviteUser(badEmail, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceInviteUserBadUserId() {

        service.inviteUser(goodEmail, badUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }
}

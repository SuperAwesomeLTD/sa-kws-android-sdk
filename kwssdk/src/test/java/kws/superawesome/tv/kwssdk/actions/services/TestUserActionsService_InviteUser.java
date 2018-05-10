package kws.superawesome.tv.kwssdk.actions.services;

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
    public void test_UserActionsService_InviteUser_OK() {

        service.inviteUser(goodEmail, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void test_UserActionsService_InviteUser_Bad_Empty_Email() {
        service.inviteUser("", goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_UserActionsService_InviteUser_Bad_Null_Email() {
        service.inviteUser(null, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test__UserActionsService_Invite_User_Bad_Email() {

        service.inviteUser(badEmail, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void test_UserActionsService_InviteUser_Bad_User_Id() {

        service.inviteUser(goodEmail, badUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }
}

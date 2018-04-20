package kws.superawesome.tv.kwssdk.actions.services;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserActionsService_SetAppData extends TestUserActionsService {

    private String goodKey = "new_value_35";
    private String badKey = "";

    private int value = 35;

    //
    //Set App Data
    @Test
    public void test_UserActionsService_SetAppData_OK() {

        service.setAppData(value, goodKey, goodUserId, goodAppId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test_UserActionsService_SetAppData_Empty_Name_Value() {

        service.setAppData(value, badKey, goodUserId, goodAppId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test(expected = IllegalArgumentException.class)
    public void test_UserActionsService_SetAppData_Null_Name_Value() {

        service.setAppData(value, null, goodUserId, goodAppId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void test_UserActionsService_SetAppData_Bad_App_ID() {

        service.setAppData(value, goodKey, goodUserId, badAppId, goodMockedToken, new Function1<Throwable, Unit>() {

            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test_UserActionsService_SetAppData_Bad_User_Id() {

        service.setAppData(value, goodKey, badUserId, badAppId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }
}

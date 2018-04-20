package kws.superawesome.tv.kwssdk.actions.services;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserActionsService_TriggerEvent extends TestUserActionsService {

    private int points = 20;
    private String goodEventId = "good_event_token";
    private String badEventId = "bad_event_token";

    //
    // Trigger Event
    @Test
    public void test_UserActionsService_Service_Trigger_Event_OK() {

        service.triggerEvent(goodEventId, points, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test_UserActionsService_TriggerEvent_Bad_User_Id() {

        service.triggerEvent(goodEventId, points, badUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void test_UserActionsService_TriggerEvent_Bad_Event_Id() {

        service.triggerEvent(badEventId, points, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }
}

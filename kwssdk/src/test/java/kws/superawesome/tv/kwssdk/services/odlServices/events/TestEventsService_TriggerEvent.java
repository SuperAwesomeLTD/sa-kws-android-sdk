package kws.superawesome.tv.kwssdk.services.odlServices.events;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestEventsService_TriggerEvent extends TestEventsService {

    private int points = 20;
    private String goodEventToken = "good_event_token";
    private String badEventToken = "bad_event_token";

    //
    // Trigger Event
    @Test
    public void testEventsServiceTriggerEventOK() {

        service.triggerEvent(points, goodUserId, goodMockedToken, goodEventToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertTrue(aSuccess);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsServiceTriggerEventBadUserId() {

        service.triggerEvent(points, badUserId, goodMockedToken, goodEventToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsServiceTriggerEventBadEventToken() {

        service.triggerEvent(points, goodUserId, goodMockedToken, badEventToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

package kws.superawesome.tv.kwssdk.services.events;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.HasTriggeredEvent;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestEventsService_HasTriggeredEvent extends TestEventsService {

    private int goodEventId = 10;
    private int badEventId = 1;

    //
    // Has Triggered Event
    @Test
    public void testEventsServiceHasTriggeredEventOK() {

        service.hasTriggeredEvent(goodUserId, goodEventId, goodMockedToken, new Function2<HasTriggeredEvent, Throwable, Unit>() {
            @Override
            public Unit invoke(HasTriggeredEvent hasTriggeredEvent, Throwable throwable) {

                Assert.assertNotNull(hasTriggeredEvent);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsServiceHasTriggeredEventBadUserId() {

        service.hasTriggeredEvent(badUserId, goodEventId, goodMockedToken, new Function2<HasTriggeredEvent, Throwable, Unit>() {
            @Override
            public Unit invoke(HasTriggeredEvent hasTriggeredEvent, Throwable throwable) {

                Assert.assertNull(hasTriggeredEvent);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsServiceHasTriggeredEventBadEventId() {

        service.hasTriggeredEvent(goodUserId, badEventId, goodMockedToken, new Function2<HasTriggeredEvent, Throwable, Unit>() {
            @Override
            public Unit invoke(HasTriggeredEvent hasTriggeredEvent, Throwable throwable) {

                Assert.assertNull(hasTriggeredEvent);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}

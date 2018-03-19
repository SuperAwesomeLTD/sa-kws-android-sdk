package kws.superawesome.tv.kwssdk.services.useractions;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.models.score.IHasTriggeredEventModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserActionsService_HasTriggeredEvent extends TestUserActionsService {

    private int goodEventId = 10;
    private int badEventId = 1;

    //
    // Has Triggered Event
    @Test
    public void testEventsServiceHasTriggeredEventOK() {

        service.hasTriggeredEvent(goodEventId, goodUserId, goodMockedToken, new Function2<IHasTriggeredEventModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IHasTriggeredEventModel hasTriggeredEvent, Throwable throwable) {

                Assert.assertNotNull(hasTriggeredEvent);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsServiceHasTriggeredEventBadUserId() {

        service.hasTriggeredEvent(goodEventId, badUserId, goodMockedToken, new Function2<IHasTriggeredEventModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IHasTriggeredEventModel hasTriggeredEvent, Throwable throwable) {

                Assert.assertNull(hasTriggeredEvent);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsServiceHasTriggeredEventBadEventId() {

        service.hasTriggeredEvent(badEventId, goodUserId, goodMockedToken, new Function2<IHasTriggeredEventModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IHasTriggeredEventModel hasTriggeredEvent, Throwable throwable) {

                Assert.assertNull(hasTriggeredEvent);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}
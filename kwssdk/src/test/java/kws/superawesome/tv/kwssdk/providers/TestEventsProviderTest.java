package kws.superawesome.tv.kwssdk.providers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.providers.EventsProvider;
import kws.superawesome.tv.kwssdk.base.responses.HasTriggeredEvent;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestEventsProviderTest extends TestBaseProvider {

    //class to test
    private EventsProvider provider;

    //given
    private int points = 20;

    private int goodEventId = 10;
    private int badEventId = 1;

    private int goodUserId = 25;
    private int badUserId = 0;

    private String goodMockedToken = "good_token";

    private String goodEventToken = "good_event_token";
    private String badEventToken = "bad_event_token";


    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //when
        // init class to test
        provider = new EventsProvider(environment, task);

    }

    //
    // Trigger Event
    @Test
    public void testEventsProviderTriggerEventOK() {

        provider.triggerEvent(points, goodUserId, goodMockedToken, goodEventToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertTrue(aSuccess);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsProviderTriggerEventBadUserId() {

        provider.triggerEvent(points, badUserId, goodMockedToken, goodEventToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsProviderTriggerEventBadEventToken() {

        provider.triggerEvent(points, goodUserId, goodMockedToken, badEventToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    //
    // Has Triggered Event
    @Test
    public void testEventsProviderHasTriggeredEventOK() {

        provider.hasTriggeredEvent(goodUserId, goodEventId, goodMockedToken, new Function2<HasTriggeredEvent, Throwable, Unit>() {
            @Override
            public Unit invoke(HasTriggeredEvent hasTriggeredEvent, Throwable throwable) {

                Assert.assertNotNull(hasTriggeredEvent);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsProviderHasTriggeredEventBadUserId() {

        provider.hasTriggeredEvent(badUserId, goodEventId, goodMockedToken, new Function2<HasTriggeredEvent, Throwable, Unit>() {
            @Override
            public Unit invoke(HasTriggeredEvent hasTriggeredEvent, Throwable throwable) {

                Assert.assertNull(hasTriggeredEvent);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testEventsProviderHasTriggeredEventBadEventId() {

        provider.hasTriggeredEvent(goodUserId, badEventId, goodMockedToken, new Function2<HasTriggeredEvent, Throwable, Unit>() {
            @Override
            public Unit invoke(HasTriggeredEvent hasTriggeredEvent, Throwable throwable) {

                Assert.assertNull(hasTriggeredEvent);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}

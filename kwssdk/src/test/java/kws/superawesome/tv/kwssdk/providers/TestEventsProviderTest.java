package kws.superawesome.tv.kwssdk.providers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.providers.EventsProvider;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestEventsProviderTest extends TestBaseProvider {

    //class to test
    private EventsProvider provider;

    private int points, eventId, goodUserId, badUserId;
    private String goodMockedToken, goodEventToken, badEventToken;

    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();

        //given
        points = 20;
        eventId = 10;

        goodUserId = 25;
        badUserId = 0;

        goodMockedToken = "good_token";

        goodEventToken = "good_event_token";
        badEventToken = "bad_event_token";

        //when
        // init class to test
        provider = new EventsProvider(environment, task);

    }

    @Test
    public void testEventsProviderOK() {

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
    public void testEventsProviderBadUserId() {

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
    public void testEventsProviderBadEventToken() {

        provider.triggerEvent(points, badUserId, goodMockedToken, badEventToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}

package kws.superawesome.tv.kwssdk.services.random_username;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.responses.RandomUsername;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestRandomUsernameService_GetRandomUsername extends TestRandomUsernameService {

    private int goodAppId = 2;
    private int badAppId = 0;


    @Test
    public void testRandomUsernameServiceRandomUsernameOK() {

        service.getRandomUsername(environment, goodAppId, new Function2<RandomUsername, Throwable, Unit>() {
            @Override
            public Unit invoke(RandomUsername randomUsername, Throwable throwable) {

                Assert.assertNotNull(randomUsername);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testRandomUsernameServiceRandomUsernameBadAppId() {

        service.getRandomUsername(environment, badAppId, new Function2<RandomUsername, Throwable, Unit>() {
            @Override
            public Unit invoke(RandomUsername randomUsername, Throwable throwable) {

                Assert.assertNull(randomUsername);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

}

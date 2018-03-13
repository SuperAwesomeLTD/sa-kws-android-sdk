package kws.superawesome.tv.kwssdk.services.user;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.Score;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserService_GetScore extends TestUserService {


    //for Get Score
    @Test
    public void testUserServiceGetScoreOK() {

        service.getScore(goodAppId, goodMockedToken, new Function2<Score, Throwable, Unit>() {
            @Override
            public Unit invoke(Score score, Throwable throwable) {

                Assert.assertNotNull(score);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testUserServiceGetScoreBadUserId() {

        service.getScore(badAppId, goodMockedToken, new Function2<Score, Throwable, Unit>() {
            @Override
            public Unit invoke(Score score, Throwable throwable) {

                Assert.assertNull(score);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }


}

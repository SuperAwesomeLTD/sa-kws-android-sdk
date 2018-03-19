package kws.superawesome.tv.kwssdk.services.scoring;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.models.score.IScoreModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestScoringService_GetScore extends TestScoringService {


    //for Get Score
    @Test
    public void testUserServiceGetScoreOK() {

        service.getScore(goodAppId, goodMockedToken, new Function2<IScoreModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IScoreModel score, Throwable throwable) {

                Assert.assertNotNull(score);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testUserServiceGetScoreBadUserId() {

        service.getScore(badAppId, goodMockedToken, new Function2<IScoreModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IScoreModel score, Throwable throwable) {

                Assert.assertNull(score);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }


}

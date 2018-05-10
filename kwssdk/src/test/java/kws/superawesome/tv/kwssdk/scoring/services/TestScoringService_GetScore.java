package kws.superawesome.tv.kwssdk.scoring.services;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.score.models.IScoreModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestScoringService_GetScore extends TestScoringService {


    //for Get ScoreModel
    @Test
    public void test_ScoringService_GetScore_OK() {

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
    public void test_ScoringService_GetScore_Bad_User_Id() {

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

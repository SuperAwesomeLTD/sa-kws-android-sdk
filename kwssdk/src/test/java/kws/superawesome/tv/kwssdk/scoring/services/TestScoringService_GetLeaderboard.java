package kws.superawesome.tv.kwssdk.scoring.services;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.models.score.ILeaderWrapperModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestScoringService_GetLeaderboard extends TestScoringService {

    //
    //Get LeadersWrapperModel
    @Test
    public void test_ScoringService_Get_Leaders_OK() {

        service.getLeaderboard(goodAppId, goodMockedToken, new Function2<ILeaderWrapperModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILeaderWrapperModel leadersWrapper, Throwable throwable) {


                Assert.assertNotNull(leadersWrapper.getResults());
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void test_ScoringService_GetLeaders_Bad_App_Id_Response() {
        service.getLeaderboard(badAppId, goodMockedToken, new Function2<ILeaderWrapperModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILeaderWrapperModel leadersWrapper, Throwable throwable) {

                Assert.assertNull(leadersWrapper);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test(expected = NumberFormatException.class)
    public void test_ScoringService_GetLeaders_App_Id_Exception() throws Throwable {

        service.getLeaderboard(Integer.valueOf("badAppId"), goodMockedToken, new Function2<ILeaderWrapperModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILeaderWrapperModel leadersWrapper, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(leadersWrapper);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


    @Test(expected = IllegalArgumentException.class)
    public void test_ScoringService_GetLeaders_Fail_NullToken() throws Throwable {
        service.getLeaderboard(goodAppId, null, new Function2<ILeaderWrapperModel, Throwable, Unit>() {
            @Override
            public Unit invoke(ILeaderWrapperModel leadersWrapper, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(leadersWrapper);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

}

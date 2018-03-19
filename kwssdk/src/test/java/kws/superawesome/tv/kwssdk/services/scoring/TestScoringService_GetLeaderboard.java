package kws.superawesome.tv.kwssdk.services.scoring;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.LeadersWrapper;
import kws.superawesome.tv.kwssdk.services.useractions.TestUserActionsService;
import tv.superawesome.protobufs.models.score.ILeaderWrapperModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestScoringService_GetLeaderboard extends TestScoringService {

    //
    //Get LeadersWrapper
    @Test
    public void testAppServiceGetLeadersOK() {

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
    public void testAppServiceGetLeadersBadAppIdResponse() {
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
    public void testAppServiceGetLeadersAppIdException() throws Throwable {

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
    public void testAppServiceGetLeadersFailNullToken() throws Throwable {
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

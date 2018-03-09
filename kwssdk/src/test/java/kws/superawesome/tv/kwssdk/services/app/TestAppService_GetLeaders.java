package kws.superawesome.tv.kwssdk.services.app;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.Leaders;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestAppService_GetLeaders extends TestAppService {

    //
    //Get Leaders
    @Test
    public void testAppServiceGetLeadersOK() {

        service.getLeaders(goodAppId, goodMockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {


                Assert.assertNotNull(leaders.getResults());
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppServiceGetLeadersBadAppIdResponse() {
        service.getLeaders(badAppId, goodMockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                Assert.assertNull(leaders);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test(expected = NumberFormatException.class)
    public void testAppServiceGetLeadersAppIdException() throws Throwable {
        service.getLeaders(Integer.valueOf("badAppId"), goodMockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(leaders);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


    @Test(expected = IllegalArgumentException.class)
    public void testAppServiceGetLeadersFailNullToken() throws Throwable {
        service.getLeaders(goodAppId, null, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(leaders);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

}

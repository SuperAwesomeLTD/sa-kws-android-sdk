package kws.superawesome.tv.kwssdk.providers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.providers.AppProvider;
import kws.superawesome.tv.kwssdk.base.responses.AppData;
import kws.superawesome.tv.kwssdk.base.responses.Leaders;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestAppProviderTest extends TestBaseProvider {

    //class to test
    private AppProvider provider;

    private int goodAppId = 2;
    private int badAppId = 0;

    private int goodUserId = 25;
    private int badUserId = 0;

    private String goodMockedToken ="good_token";

    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //when
        // init class to test
        provider = new AppProvider(environment, task);

    }


    //
    //Get Leaders
    @Test
    public void testAppProviderGetLeadersOK() {

        provider.getLeaders(goodAppId, goodMockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {


                Assert.assertNotNull(leaders.getResults());
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppProviderGetLeadersBadAppIdResponse() {
        provider.getLeaders(badAppId, goodMockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                Assert.assertNull(leaders);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test(expected = NumberFormatException.class)
    public void testAppProviderGetLeadersAppIdException() throws Throwable {
        provider.getLeaders(Integer.valueOf("badAppId"), goodMockedToken, new Function2<Leaders, Throwable, Unit>() {
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
    public void testAppProviderGetLeadersFailNullToken() throws Throwable {
        provider.getLeaders(goodAppId, null, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                //todo is this needed when expecting an exception?
                Assert.assertNull(leaders);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


    //
    //Set App Data
    @Test
    public void testAppProviderSetAppDataOK() {

        provider.setAppData(goodAppId, goodUserId, "new_value_35", 35, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertTrue(aBoolean);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppProviderSetAppDataEmptyNameValue() {

        provider.setAppData(goodAppId, goodUserId, "", 35, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertFalse(aBoolean);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAppProviderSetAppDataNullNameValue() {

        provider.setAppData(goodAppId, goodUserId, null, 35, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {


                //todo is this needed when expecting an exception?
                Assert.assertFalse(aBoolean);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppProviderSetAppDataBadAppID() {

        provider.setAppData(badAppId, goodUserId, "new_value_35", 35, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertFalse(aBoolean);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppProviderSetAppDataBadUserId() {

        provider.setAppData(goodAppId, badUserId, "new_value_35", 35, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertFalse(aBoolean);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    //
    //Get App Data
    @Test
    public void testAppProviderGetAppDataOK() {

        provider.getAppData(goodAppId, goodUserId, goodMockedToken, new Function2<AppData, Throwable, Unit>() {
            @Override
            public Unit invoke(AppData appData, Throwable throwable) {

                Assert.assertNotNull(appData);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppProviderGetAppDataBadAppId() {

        provider.getAppData(badAppId, goodUserId, goodMockedToken, new Function2<AppData, Throwable, Unit>() {
            @Override
            public Unit invoke(AppData appData, Throwable throwable) {

                Assert.assertNull(appData);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppProviderGetAppDataBadUserId() {

        provider.getAppData(badAppId, goodUserId, goodMockedToken, new Function2<AppData, Throwable, Unit>() {
            @Override
            public Unit invoke(AppData appData, Throwable throwable) {

                Assert.assertNull(appData);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }


}

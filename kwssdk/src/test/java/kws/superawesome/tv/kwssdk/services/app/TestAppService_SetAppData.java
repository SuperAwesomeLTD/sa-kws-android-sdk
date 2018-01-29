package kws.superawesome.tv.kwssdk.services.app;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestAppService_SetAppData extends TestAppService {

    private String goodValue = "new_value_35";
    private String badValue = "";

    private int goodNumericValue = 35;

    //
    //Set App Data
    @Test
    public void testAppServiceSetAppDataOK() {

        service.setAppData(goodAppId, goodUserId, goodValue, goodNumericValue, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertTrue(aBoolean);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppServiceSetAppDataEmptyNameValue() {

        service.setAppData(goodAppId, goodUserId, badValue, goodNumericValue, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertFalse(aBoolean);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAppServiceSetAppDataNullNameValue() {

        service.setAppData(goodAppId, goodUserId, null, goodNumericValue, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
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
    public void testAppServiceSetAppDataBadAppID() {

        service.setAppData(badAppId, goodUserId, goodValue, goodNumericValue, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertFalse(aBoolean);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppServiceSetAppDataBadUserId() {

        service.setAppData(goodAppId, badUserId, goodValue, goodNumericValue, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean, Throwable throwable) {

                Assert.assertFalse(aBoolean);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}

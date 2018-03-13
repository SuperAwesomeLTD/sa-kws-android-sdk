package kws.superawesome.tv.kwssdk.services.app;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.AppDataWrapper;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestAppService_GetAppDataWrapper extends TestAppService {


    //
    //Get App Data
    @Test
    public void testAppServiceGetAppDataOK() {

        service.getAppData(goodAppId, goodUserId, goodMockedToken, new Function2<AppDataWrapper, Throwable, Unit>() {
            @Override
            public Unit invoke(AppDataWrapper appDataWrapper, Throwable throwable) {

                Assert.assertNotNull(appDataWrapper);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppServiceGetAppDataBadAppId() {

        service.getAppData(badAppId, goodUserId, goodMockedToken, new Function2<AppDataWrapper, Throwable, Unit>() {
            @Override
            public Unit invoke(AppDataWrapper appDataWrapper, Throwable throwable) {

                Assert.assertNull(appDataWrapper);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppServiceGetAppDataBadUserId() {

        service.getAppData(badAppId, goodUserId, goodMockedToken, new Function2<AppDataWrapper, Throwable, Unit>() {
            @Override
            public Unit invoke(AppDataWrapper appDataWrapper, Throwable throwable) {

                Assert.assertNull(appDataWrapper);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }

}

package kws.superawesome.tv.kwssdk.services.app;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.responses.AppData;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestAppService_GetAppData extends TestAppService {


    //
    //Get App Data
    @Test
    public void testAppServiceGetAppDataOK() {

        service.getAppData(goodAppId, goodUserId, goodMockedToken, new Function2<AppData, Throwable, Unit>() {
            @Override
            public Unit invoke(AppData appData, Throwable throwable) {

                Assert.assertNotNull(appData);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppServiceGetAppDataBadAppId() {

        service.getAppData(badAppId, goodUserId, goodMockedToken, new Function2<AppData, Throwable, Unit>() {
            @Override
            public Unit invoke(AppData appData, Throwable throwable) {

                Assert.assertNull(appData);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppServiceGetAppDataBadUserId() {

        service.getAppData(badAppId, goodUserId, goodMockedToken, new Function2<AppData, Throwable, Unit>() {
            @Override
            public Unit invoke(AppData appData, Throwable throwable) {

                Assert.assertNull(appData);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }

}

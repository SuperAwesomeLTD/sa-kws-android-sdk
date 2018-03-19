package kws.superawesome.tv.kwssdk.services.useractions;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.models.appdata.IAppDataWrapperModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserActionsService_GetAppData extends TestUserActionsService {


    //
    //Get App Data
    @Test
    public void testAppServiceGetAppDataOK() {

        service.getAppData(goodUserId, goodAppId, goodMockedToken, new Function2<IAppDataWrapperModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IAppDataWrapperModel appDataWrapper, Throwable throwable) {

                Assert.assertNotNull(appDataWrapper);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppServiceGetAppDataBadAppId() {

        service.getAppData(goodUserId, badAppId, goodMockedToken, new Function2<IAppDataWrapperModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IAppDataWrapperModel appDataWrapper, Throwable throwable) {

                Assert.assertNull(appDataWrapper);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testAppServiceGetAppDataBadUserId() {

        service.getAppData(goodUserId, badAppId, goodMockedToken, new Function2<IAppDataWrapperModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IAppDataWrapperModel appDataWrapper, Throwable throwable) {

                Assert.assertNull(appDataWrapper);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }

}

package kws.superawesome.tv.kwssdk.actions.services;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.actions.models.IAppDataWrapperModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserActionsService_GetAppData extends TestUserActionsService {

    //
    //Get App Data
    @Test
    public void test_UserActionsService_GetAppData_OK() {

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
    public void test_UserActionsService_GetAppData_Bad_AppId() {

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
    public void test_UserActionsService_GetAppData_Bad_UserId() {

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

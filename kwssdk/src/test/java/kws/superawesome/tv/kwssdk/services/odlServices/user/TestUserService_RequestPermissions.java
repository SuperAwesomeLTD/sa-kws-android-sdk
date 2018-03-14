package kws.superawesome.tv.kwssdk.services.odlServices.user;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserService_RequestPermissions extends TestUserService {

    //for Request Permissions

    @Test
    public void testUserServiceRequestPermissionOK() {
        List<String> listOfPermissions = new ArrayList<>();
        listOfPermissions.add(goodPermissionString);

        service.requestPermissions(goodUserId, goodMockedToken, listOfPermissions, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertTrue(aSuccess);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceRequestPermissionBadPermission() {
        List<String> listOfPermissions = new ArrayList<>();
        listOfPermissions.add(badPermissionString);

        service.requestPermissions(goodUserId, goodMockedToken, listOfPermissions, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceRequestPermissionBadUserId() {
        List<String> listOfPermissions = new ArrayList<>();

        service.requestPermissions(badUserId, goodMockedToken, listOfPermissions, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}

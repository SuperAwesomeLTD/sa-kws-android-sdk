package kws.superawesome.tv.kwssdk.services.useractions;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserActionsService_RequestPermissions extends TestUserActionsService {

    //for Request Permissions

    @Test
    public void testUserServiceRequestPermissionOK() {
        List<String> listOfPermissions = new ArrayList<>();
        listOfPermissions.add(goodPermissionString);

        service.requestPermissions(listOfPermissions, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceRequestPermissionBadPermission() {
        List<String> listOfPermissions = new ArrayList<>();
        listOfPermissions.add(badPermissionString);

        service.requestPermissions(listOfPermissions, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceRequestPermissionBadUserId() {
        List<String> listOfPermissions = new ArrayList<>();

        service.requestPermissions(listOfPermissions, badUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

}

package kws.superawesome.tv.kwssdk.services.user;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class TestUserService_UpdateUserDetails extends TestUserService {

    @Test
    public void testUserServiceSetUserDetailsOK() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("firstName", "John");
        details.put("lastName", "Droid");

        HashMap<String, Object> addressMap = new HashMap<>();
        addressMap.put("street","Number Two");
        addressMap.put("city","London");
        addressMap.put("postCode","xyz");
        addressMap.put("country","GB");
        details.put("address", addressMap);


        service.updateUser(details, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);
                return null;
            }
        });
    }

    @Test
    public void testUserServiceSetUserDetailsBadUserId() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("firstName", "John");
        details.put("lastName", "Droid");

        service.updateUser(details, badUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);
                return null;
            }
        });
    }

    @Test
    public void testUserServiceSetUserDetailsBadAddressResponse() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("firstName", "John");
        details.put("lastName", "Droid");

        HashMap<String, Object> addressMap = new HashMap<>();
        addressMap.put("","");
        details.put("address", addressMap);


        service.updateUser(details, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);
                return null;
            }
        });

    }

    @Test
    public void testUserServiceSetUserDetailsParentEmailOK() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("parentEmail", "email@email.com");

        service.updateUser(details, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);
                return null;
            }
        });
    }

    @Test
    public void testUserServiceSetUserDetailsParentInvalidResponse() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("parentEmail", "");

        service.updateUser(details, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);
                return null;
            }
        });
    }

    @Test
    public void testUserServiceSetUserDetailsParentAlreadySetResponse() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("parentEmail", "already_set");

        service.updateUser(details, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNotNull(throwable);
                return null;
            }
        });
    }

}

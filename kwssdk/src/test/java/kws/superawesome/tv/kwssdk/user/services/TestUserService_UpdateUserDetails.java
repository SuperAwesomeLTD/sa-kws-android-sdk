package kws.superawesome.tv.kwssdk.user.services;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class TestUserService_UpdateUserDetails extends TestUserService {

    @Test
    public void test_UserService_SetUserDetails_OK() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("firstName", "John");
        details.put("lastName", "Droid");

        JSONObject addressObj = new JSONObject();
        try {
            addressObj.put("street", "Number Two");
            addressObj.put("city", "London");
            addressObj.put("postCode", "xyz");
            addressObj.put("country", "GB");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        details.put("address", addressObj);


        service.updateUser(details, goodUserId, goodMockedToken, new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {

                Assert.assertNull(throwable);
                return null;
            }
        });
    }

    @Test
    public void test_UserService_SetUserDetails_BadUserId() {

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
    public void test_UserService_SetUserDetails_BadAddressResponse() {

        HashMap<String, Object> details = new HashMap<>();

        details.put("firstName", "John");
        details.put("lastName", "Droid");

        HashMap<String, Object> addressMap = new HashMap<>();
        addressMap.put("", "");
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
    public void test_UserService_SetUserDetails_ParentEmailOK() {

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
    public void test_UserService_SetUserDetails_ParentInvalidResponse() {

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
    public void test_UserService_SetUserDetails_Parent_Already_Set_Response() {

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

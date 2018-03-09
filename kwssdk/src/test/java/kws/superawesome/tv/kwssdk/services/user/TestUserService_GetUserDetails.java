package kws.superawesome.tv.kwssdk.services.user;


import junit.framework.Assert;

import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.models.UserDetails;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserService_GetUserDetails extends TestUserService {


    //for User Details

    @Test
    public void testUserServiceGetUserDetailsOK() {

        service.getUserDetails(goodUserId, goodMockedToken, new Function2<UserDetails, Throwable, Unit>() {
            @Override
            public Unit invoke(UserDetails userDetails, Throwable throwable) {

                Assert.assertNotNull(userDetails);
                Assert.assertNull(throwable);

                Assert.assertEquals(25, (int) userDetails.getId());
                Assert.assertEquals("username", userDetails.getName());
                Assert.assertEquals("first_name", userDetails.getFirstName());
                Assert.assertEquals("last_name", userDetails.getLastName());

                Assert.assertNotNull(userDetails.getAddress());
                Assert.assertEquals(userDetails.getAddress().getStreet(), "street");
                Assert.assertEquals(userDetails.getAddress().getCity(), "city");
                Assert.assertEquals(userDetails.getAddress().getPostCode(), "postCode");
                Assert.assertEquals(userDetails.getAddress().getCountry(), "country");

                Assert.assertEquals(userDetails.getDateOfBirth(), "dob");
                Assert.assertEquals(userDetails.getGender(), "male");
                Assert.assertEquals(userDetails.getLanguage(), "en");
                Assert.assertEquals(userDetails.getEmail(), "email@email");
                Assert.assertTrue(userDetails.getHasSetParentEmail());

                Assert.assertNotNull(userDetails.getApplicationProfile());
                Assert.assertEquals(userDetails.getApplicationProfile().getName(), "username");
                Assert.assertEquals((int) userDetails.getApplicationProfile().getCustomField1(), 0);
                Assert.assertEquals((int) userDetails.getApplicationProfile().getCustomField2(), 0);
                Assert.assertEquals((int) userDetails.getApplicationProfile().getAvatarId(), 0);

                Assert.assertNotNull(userDetails.getApplicationPermissions());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAddress());
                Assert.assertFalse(userDetails.getApplicationPermissions().getFirstName());
                Assert.assertFalse(userDetails.getApplicationPermissions().getLastName());
                Assert.assertFalse(userDetails.getApplicationPermissions().getEmail());
                Assert.assertFalse(userDetails.getApplicationPermissions().getStreetAddress());
                Assert.assertFalse(userDetails.getApplicationPermissions().getCity());
                Assert.assertFalse(userDetails.getApplicationPermissions().getPostalCode());
                Assert.assertFalse(userDetails.getApplicationPermissions().getCountry());
                Assert.assertTrue(userDetails.getApplicationPermissions().getNotifications());
                Assert.assertFalse(userDetails.getApplicationPermissions().getNewsletter());
                Assert.assertFalse(userDetails.getApplicationPermissions().getCompetition());

                Assert.assertNotNull(userDetails.getPoints());
                Assert.assertEquals((int) userDetails.getPoints().getReceived(), 600);
                Assert.assertEquals((int) userDetails.getPoints().getTotal(), 600);
                Assert.assertEquals((int) userDetails.getPoints().getInApp(), 600);
                Assert.assertEquals((int) userDetails.getPoints().getBalance(), 600);
                Assert.assertEquals((int) userDetails.getPoints().getPending(), 1);

                return null;
            }
        });

    }

    @Test
    public void testUserServiceGetUserDetailsBadUserId() {

        service.getUserDetails(badUserId, goodMockedToken, new Function2<UserDetails, Throwable, Unit>() {
            @Override
            public Unit invoke(UserDetails userDetails, Throwable throwable) {

                Assert.assertNull(userDetails);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

package kws.superawesome.tv.kwssdk.services.user;


import junit.framework.Assert;

import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.responses.UserDetails;

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
                Assert.assertEquals("username", userDetails.getUsername());
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
                Assert.assertEquals(userDetails.getApplicationProfile().getUsername(), "username");
                Assert.assertEquals((int) userDetails.getApplicationProfile().getCustomField1(), 0);
                Assert.assertEquals((int) userDetails.getApplicationProfile().getCustomField2(), 0);
                Assert.assertEquals((int) userDetails.getApplicationProfile().getAvatarId(), 0);

                Assert.assertNotNull(userDetails.getApplicationPermissions());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessAddress());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessFirstName());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessLastName());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessEmail());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessStreetAddress());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessCity());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessPostalCode());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAccessCountry());
                Assert.assertTrue(userDetails.getApplicationPermissions().getSendPushNotification());
                Assert.assertFalse(userDetails.getApplicationPermissions().getSendNewsletter());
                Assert.assertFalse(userDetails.getApplicationPermissions().getEnterCompetitions());

                Assert.assertNotNull(userDetails.getPoints());
                Assert.assertEquals((int) userDetails.getPoints().getTotalReceived(), 600);
                Assert.assertEquals((int) userDetails.getPoints().getTotal(), 600);
                Assert.assertEquals((int) userDetails.getPoints().getTotalPointsReceivedInCurrentApp(), 600);
                Assert.assertEquals((int) userDetails.getPoints().getAvailableBalance(), 600);
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

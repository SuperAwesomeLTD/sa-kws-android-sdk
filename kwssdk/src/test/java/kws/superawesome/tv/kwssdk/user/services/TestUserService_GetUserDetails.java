package kws.superawesome.tv.kwssdk.user.services;


import junit.framework.Assert;

import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import tv.superawesome.protobufs.models.user.IUserDetailsModel;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestUserService_GetUserDetails extends TestUserService {


    //for User Details

    @Test
    public void test_UserService_GetUserDetails_OK() {

        service.getUser(goodUserId, goodMockedToken, new Function2<IUserDetailsModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IUserDetailsModel userDetails, Throwable throwable) {

                Assert.assertNotNull(userDetails);
                Assert.assertNull(throwable);

                Assert.assertNotNull(userDetails);
                Assert.assertEquals(userDetails.getId(), (Integer) 25);
                Assert.assertEquals(userDetails.getName(), "genioustiger123");
                Assert.assertEquals(userDetails.getFirstName(), "John");
                Assert.assertEquals(userDetails.getLastName(), "Doe");
                Assert.assertEquals(userDetails.getDateOfBirth(), "2012-03-02");
                Assert.assertEquals(userDetails.getGender(), "male");
                Assert.assertEquals(userDetails.getLanguage(), "en");
                Assert.assertEquals(userDetails.getEmail(), "john.doe@email");

                Assert.assertTrue(userDetails.getHasSetParentEmail());

                Assert.assertEquals(userDetails.getConsentAgeForCountry(), 13);

                Assert.assertTrue(userDetails.isMinor());

                Assert.assertNotNull(userDetails.getAddress());
                Assert.assertEquals(userDetails.getAddress().getStreet(), "Number 12");
                Assert.assertEquals(userDetails.getAddress().getCity(), "London");
                Assert.assertEquals(userDetails.getAddress().getPostCode(), "NW1 23L");
                Assert.assertEquals(userDetails.getAddress().getCountry(), "United Kingdom");
                Assert.assertEquals(userDetails.getAddress().getCountryCode(), "GB");
                Assert.assertEquals(userDetails.getAddress().getCountryName(), "United Kingdom");

                Assert.assertNotNull(userDetails.getApplicationProfile());
                Assert.assertEquals(userDetails.getApplicationProfile().getName(), "genioustiger123");
                Assert.assertEquals(userDetails.getApplicationProfile().getCustomField1(), (Integer) 0);
                Assert.assertEquals(userDetails.getApplicationProfile().getCustomField2(), (Integer) 0);
                Assert.assertEquals(userDetails.getApplicationProfile().getAvatarId(), (Integer) 0);

                Assert.assertNotNull(userDetails.getApplicationPermissions());
                Assert.assertTrue(userDetails.getApplicationPermissions().getNotifications());
                Assert.assertFalse(userDetails.getApplicationPermissions().getAddress());
                Assert.assertFalse(userDetails.getApplicationPermissions().getFirstName());
                Assert.assertFalse(userDetails.getApplicationPermissions().getLastName());
                Assert.assertFalse(userDetails.getApplicationPermissions().getEmail());
                Assert.assertFalse(userDetails.getApplicationPermissions().getStreetAddress());
                Assert.assertFalse(userDetails.getApplicationPermissions().getCity());
                Assert.assertFalse(userDetails.getApplicationPermissions().getPostalCode());
                Assert.assertFalse(userDetails.getApplicationPermissions().getCountry());
                Assert.assertFalse(userDetails.getApplicationPermissions().getNewsletter());
                Assert.assertFalse(userDetails.getApplicationPermissions().getCompetition());

                Assert.assertNotNull(userDetails.getPoints());
                Assert.assertEquals(userDetails.getPoints().getReceived(), (Integer) 600);
                Assert.assertEquals(userDetails.getPoints().getTotal(), (Integer) 600);
                Assert.assertEquals(userDetails.getPoints().getInApp(), (Integer) 600);
                Assert.assertEquals(userDetails.getPoints().getBalance(), (Integer) 600);
                Assert.assertEquals(userDetails.getPoints().getPending(), (Integer) 1);

                Assert.assertEquals(userDetails.getCreatedAt(), "2018-01-19");

                return null;
            }
        });

    }

    @Test
    public void test_UserService_GetUserDetails_BadUserId() {

        service.getUser(badUserId, goodMockedToken, new Function2<IUserDetailsModel, Throwable, Unit>() {
            @Override
            public Unit invoke(IUserDetailsModel userDetails, Throwable throwable) {

                Assert.assertNull(userDetails);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


}

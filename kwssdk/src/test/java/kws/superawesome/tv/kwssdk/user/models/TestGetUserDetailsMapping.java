package kws.superawesome.tv.kwssdk.user.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.user.models.UserDetailsModel;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestGetUserDetailsMapping {

    @Test
    public void test_GetUserDetails_Mapping_Response_Success(){
        String json = ResourceReader.readResource("mock_get_user_details_success_response.json");
        UserDetailsModel userDetailsModel = (UserDetailsModel) new ParseJsonTask(UserDetailsModel.class).execute(json).take();

        Assert.assertNotNull(userDetailsModel);
        Assert.assertEquals(userDetailsModel.getId(), (Integer) 25);
        Assert.assertEquals(userDetailsModel.getName(), "genioustiger123");
        Assert.assertEquals(userDetailsModel.getFirstName(), "John");
        Assert.assertEquals(userDetailsModel.getLastName(), "Doe");
        Assert.assertEquals(userDetailsModel.getDateOfBirth(), "2012-03-02");
        Assert.assertEquals(userDetailsModel.getGender(), "male");
        Assert.assertEquals(userDetailsModel.getLanguage(), "en");
        Assert.assertEquals(userDetailsModel.getEmail(), "john.doe@email");

        Assert.assertTrue(userDetailsModel.getHasSetParentEmail());

        Assert.assertEquals(userDetailsModel.getConsentAgeForCountry(), 13);

        Assert.assertTrue(userDetailsModel.isMinor());

        Assert.assertNotNull(userDetailsModel.getAddress());
        Assert.assertEquals(userDetailsModel.getAddress().getStreet(), "Number 12");
        Assert.assertEquals(userDetailsModel.getAddress().getCity(), "London");
        Assert.assertEquals(userDetailsModel.getAddress().getPostCode(), "NW1 23L");
        Assert.assertEquals(userDetailsModel.getAddress().getCountry(), "United Kingdom");
        Assert.assertEquals(userDetailsModel.getAddress().getCountryCode(), "GB");
        Assert.assertEquals(userDetailsModel.getAddress().getCountryName(), "United Kingdom");

        Assert.assertNotNull(userDetailsModel.getApplicationProfile());
        Assert.assertEquals(userDetailsModel.getApplicationProfile().getName(), "genioustiger123");
        Assert.assertEquals(userDetailsModel.getApplicationProfile().getCustomField1(), (Integer) 0);
        Assert.assertEquals(userDetailsModel.getApplicationProfile().getCustomField2(), (Integer) 0);
        Assert.assertEquals(userDetailsModel.getApplicationProfile().getAvatarId(), (Integer) 0);

        Assert.assertNotNull(userDetailsModel.getApplicationPermissions());
        Assert.assertTrue(userDetailsModel.getApplicationPermissions().getNotifications());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getAddress());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getFirstName());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getLastName());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getEmail());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getStreetAddress());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getCity());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getPostalCode());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getCountry());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getNewsletter());
        Assert.assertFalse(userDetailsModel.getApplicationPermissions().getCompetition());

        Assert.assertNotNull(userDetailsModel.getPoints());
        Assert.assertEquals(userDetailsModel.getPoints().getReceived(), (Integer) 600);
        Assert.assertEquals(userDetailsModel.getPoints().getTotal(), (Integer) 600);
        Assert.assertEquals(userDetailsModel.getPoints().getInApp(), (Integer) 600);
        Assert.assertEquals(userDetailsModel.getPoints().getBalance(), (Integer) 600);
        Assert.assertEquals(userDetailsModel.getPoints().getPending(), (Integer) 1);

        Assert.assertEquals(userDetailsModel.getCreatedAt(), "2018-01-19");
    }

}

package kws.superawesome.tv.kwssdk.username.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;

public class TestRandomUsernameMapping {

    @Test
    public void test_RandomUsername_Mapping_Success_Response(){

        String randomUsernameString = ResourceReader.readResource("mock_get_random_username_success_response.json");

        Assert.assertEquals(randomUsernameString, "\"coolrandomusername123\"");
    }
}

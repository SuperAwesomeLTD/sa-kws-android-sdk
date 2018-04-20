package kws.superawesome.tv.kwssdk.user.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestUpdateUserDetailsMapping {

    @Test
    public void test_UpdateUserDetails_Mapping_Success_Response() {

        String json = ResourceReader.readResource("mock_generic_empty_response.json");

        Assert.assertEquals(json, "{}");
    }

    @Test
    public void test_UpdateUserDetails_Mapping_AddressFails_Response() {
        String json = ResourceReader.readResource("mock_update_user_details_address_fails_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"address\" fails because [child \"street\" fails because [\"street\" is required], child \"postCode\" fails because [\"postCode\" is required], child \"city\" fails because [\"city\" is required], child \"country\" fails because [\"country\" is required]]");
        Assert.assertNotNull(errorWrapperModel.getInvalid());
    }

    @Test
    public void test_UpdateUserDetails_Mapping_Permissions_Not_Granted_Response() {
        String json = ResourceReader.readResource("mock_update_user_details_permission_not_granted_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"country\" fails because [\"country\" with value \"a\" fails to match the required pattern: /^[A-Z]{2}$/]");
        Assert.assertNotNull(errorWrapperModel.getInvalid());
    }

    @Test
    public void test_UpdateUserDetails_Mapping_Parent_Email_Already_Set_Response() {
        String json = ResourceReader.readResource("mock_update_user_parent_email_already_set_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 10);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "conflict");
        Assert.assertEquals(errorWrapperModel.getMessage(), "parentEmail already set");
        Assert.assertNotNull(errorWrapperModel.getInvalid());
        Assert.assertNotNull(errorWrapperModel.getInvalid().getParentEmail());
        Assert.assertEquals(errorWrapperModel.getInvalid().getParentEmail().getCode(), (Integer) 10);
        Assert.assertEquals(errorWrapperModel.getInvalid().getParentEmail().getCodeMeaning(), "conflict");
        Assert.assertEquals(errorWrapperModel.getInvalid().getParentEmail().getMessage(), "parentEmail already set");
    }

    @Test
    public void test_UpdateUserDetails_Mapping_Parent_Email_Invalid_Response() {
        String json = ResourceReader.readResource("mock_update_user_parent_email_invalid_email_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"parentEmail\" fails because [\"parentEmail\" must be a valid email]");
        Assert.assertNotNull(errorWrapperModel.getInvalid());
        Assert.assertNotNull(errorWrapperModel.getInvalid().getParentEmail());
        Assert.assertEquals(errorWrapperModel.getInvalid().getParentEmail().getCode(), (Integer) 7);
        Assert.assertEquals(errorWrapperModel.getInvalid().getParentEmail().getCodeMeaning(), "invalidValue");
        Assert.assertEquals(errorWrapperModel.getInvalid().getParentEmail().getMessage(), "\"parentEmail\" must be a valid email");
    }

}

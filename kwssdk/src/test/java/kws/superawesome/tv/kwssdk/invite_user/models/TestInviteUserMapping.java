package kws.superawesome.tv.kwssdk.invite_user.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestInviteUserMapping {

    @Test
    public void test_InviteUser_Success_Response() {
        String json = ResourceReader.readResource("mock_generic_empty_response.json");

        Assert.assertEquals(json, "{}");
    }

    @Test
    public void test_InviteUser_Bad_Email_Response() {
        String json = ResourceReader.readResource("mock_invite_user_bad_email_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel ) new ParseJsonTask(ErrorWrapperModel .class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"email\" fails because [\"email\" must be a valid email]");
        Assert.assertNotNull(errorWrapperModel.getInvalid());
    }
}

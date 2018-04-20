package kws.superawesome.tv.kwssdk.authentication.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.authentication.models.AuthUserResponseModel;
import kws.superawesome.tv.kwssdk.base.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestAuthUserResponseModel {

    @Test
    public void test_AuthUserResponse_Success_Response() {

        String json = ResourceReader.readResource("mock_create_user_success_response.json");
        AuthUserResponseModel authUserResponseModel = (AuthUserResponseModel) new ParseJsonTask(AuthUserResponseModel.class).execute(json).take();

        Assert.assertNotNull(authUserResponseModel);
        Assert.assertEquals(authUserResponseModel.getToken(), "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VySWQiOjUxMDksImFwcElkIjozNTgsImNsaWVudElkIjoia3dzLXNkay10ZXN0aW5nIiwic2NvcGUiOiJ1c2VyIiwiaWF0IjoxNTI0MjI0NjAwLCJleHAiOjE4Mzk1ODQ2MDAsImlzcyI6InN1cGVyYXdlc29tZSJ9.wwlm1xMynRa3Vmz5m6LbgBkK_2NUAeZVF4IeyU16krI9And0eLk25z_2TehVu4d-Px5dSBg2BHrgHmM1Gz0-vEWsbOS-QhLuXAX4nUgJkpNHJNJ2VEWQCGUFjrc36bppAgdusWhTKcymxXNIImtNVtERKVKwD5d8goW3lsywAm116UxxosdlOIXWxN57uBuKL0_F-kczBNhjbu0PFB3dPjN-rtRCUTGp_Fw02qaghAOAH7AG4DXntvn3PN7XnUuRVVxAYXRtnSaV5V3B2UA4XHcyyjcV8iqqtYisndY2qVy8hMLMv8473Oves1SvELu8zYwW_jnq9yE4cR2QPvRroQ");
        Assert.assertEquals(authUserResponseModel.getId(), (Integer) 99);
    }

    @Test
    public void test_AuthUserResponse_Bad_Country_Response() {
        String json = ResourceReader.readResource("mock_create_user_bad_country_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"country\" fails because [\"country\" with value \"a\" fails to match the required pattern: /^[A-Z]{2}$/]");

        Assert.assertNotNull(errorWrapperModel.getInvalid());
        Assert.assertNotNull(errorWrapperModel.getInvalid().getCountry());
        Assert.assertEquals(errorWrapperModel.getInvalid().getCountry().getCode(), (Integer) 7);
        Assert.assertEquals(errorWrapperModel.getInvalid().getCountry().getCodeMeaning(), "invalidValue");
        Assert.assertEquals(errorWrapperModel.getInvalid().getCountry().getMessage(), "\"country\" with value \"a\" fails to match the required pattern: /^[A-Z]{2}$/");
    }

    @Test
    public void test_AuthUserResponse_Bad_DOB_Response() {
        String json = ResourceReader.readResource("mock_create_user_bad_dob_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"dateOfBirth\" fails because [\"dateOfBirth\" with value \"a\" fails to match the required pattern: /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/]");

        Assert.assertNotNull(errorWrapperModel.getInvalid());
        Assert.assertNotNull(errorWrapperModel.getInvalid().getDateOfBirth());
        Assert.assertEquals(errorWrapperModel.getInvalid().getDateOfBirth().getCode(), (Integer) 7);
        Assert.assertEquals(errorWrapperModel.getInvalid().getDateOfBirth().getCodeMeaning(), "invalidValue");
        Assert.assertEquals(errorWrapperModel.getInvalid().getDateOfBirth().getMessage(), "\"dateOfBirth\" with value \"a\" fails to match the required pattern: /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/");
    }

    @Test
    public void test_AuthUserResponse_Bad_ParentEmail_Response() {
        String json = ResourceReader.readResource("mock_create_user_bad_email_response.json");
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

    @Test
    public void test_AuthUserResponse_Bad_Username_Response() {
        String json = ResourceReader.readResource("mock_create_user_bad_username_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"username\" fails because [\"username\" length must be at least 3 characters long]");

        Assert.assertNotNull(errorWrapperModel.getInvalid());
        Assert.assertNotNull(errorWrapperModel.getInvalid().getUsername());
        Assert.assertEquals(errorWrapperModel.getInvalid().getUsername().getCode(), (Integer) 7);
        Assert.assertEquals(errorWrapperModel.getInvalid().getUsername().getCodeMeaning(), "invalidValue");
        Assert.assertEquals(errorWrapperModel.getInvalid().getUsername().getMessage(), "\"username\" length must be at least 3 characters long");
    }

    @Test
    public void test_AuthUserResponse_Bad_Password_Response() {
        String json = ResourceReader.readResource("mock_create_user_bad_password_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"password\" fails because [\"password\" length must be at least 8 characters long]");

        Assert.assertNotNull(errorWrapperModel.getInvalid());
        Assert.assertNotNull(errorWrapperModel.getInvalid().getPassword());
        Assert.assertEquals(errorWrapperModel.getInvalid().getPassword().getCode(), (Integer) 7);
        Assert.assertEquals(errorWrapperModel.getInvalid().getPassword().getCodeMeaning(), "invalidValue");
        Assert.assertEquals(errorWrapperModel.getInvalid().getPassword().getMessage(), "\"password\" length must be at least 8 characters long");
    }

}

package kws.superawesome.tv.kwssdk.authentication.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.common.models.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.json.ParseJsonTask;

public class TestLoginAuthResponseModel {

    @Test
    public void test_LoginAuthResponseModel_Success_Response() {
        String json = ResourceReader.readResource("mock_login_success_response.json");
        LoginAuthResponseModel loginAuthResponseModel = (LoginAuthResponseModel) new ParseJsonTask(LoginAuthResponseModel.class).execute(json).take();

        Assert.assertNotNull(loginAuthResponseModel);
        Assert.assertEquals(loginAuthResponseModel.getToken(), "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VySWQiOjUwNzMsImFwcElkIjozNTgsImNsaWVudElkIjoia3dzLXNkay10ZXN0aW5nIiwic2NvcGUiOiJ1c2VyIiwiaWF0IjoxNTI0MjI1NDA3LCJleHAiOjE4Mzk1ODU0MDcsImlzcyI6InN1cGVyYXdlc29tZSJ9.EVgeuMMTZzx9ujS4OVf255ZltV1PHRGFkWYFYGYaDy_FBV9_1XOz6FDBIVDZwA9RBhuZomYmTZ85SS3x4Dlx9AR1mdm1jlqsfRb0YFlrx5rchfKdF_ezFw6_lTmij3lxDWH8tV_jolHCwB6K_oAOBEjpPCqTIS_m5_FW3Egxmcu4KdphVdCL6O28Nr6W5PoZdxTW8NBVaJLXboxonqYylwoeET_MPMSwrRSSKOKi9RqY4F-Hn7HZWhi13Ro16sEz3F_SQUC03_z7wbOyl8V_WoV6XAkQ6eOfGkfkc0PyJkt8arp3XYxxhC-iIBvcwe5EboJcRNCWeVokVkSpsmJ21A");
    }

    @Test
    public void test_LoginAuthResponseModel_Invalid_Credentials_Response() {
        String json = ResourceReader.readResource("mock_login_bad_credentials_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getErrorCode(), "invalid_grant");
        Assert.assertEquals(errorWrapperModel.getError(), "User credentials are invalid");
    }

    @Test
    public void test_LoginAuthResponseModel_Missing_Credentials_Response() {
        String json = ResourceReader.readResource("mock_login_missing_credentials_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getErrorCode(), "invalid_client");
        Assert.assertEquals(errorWrapperModel.getError(), "Missing parameters. \"username\" and \"password\" are required");
    }
}

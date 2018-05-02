package kws.superawesome.tv.kwssdk.config.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.common.models.error.ErrorWrapperModel;
import kws.superawesome.tv.kwssdk.base.config.models.AppConfigWrapperModel;
import tv.superawesome.samobilebase.json.ParseJsonTask;

public class TestAppConfigMapping {

    @Test
    public void test_AppConfig_Mapping_Success_Response() {

        String json = ResourceReader.readResource("mock_get_app_config_success_response.json");
        AppConfigWrapperModel appConfigWrapper = (AppConfigWrapperModel) new ParseJsonTask(AppConfigWrapperModel.class).execute(json).take();

        Assert.assertNotNull(appConfigWrapper);
        Assert.assertNotNull(appConfigWrapper.getApp());
        Assert.assertEquals(appConfigWrapper.getApp().getId(), 2);
        Assert.assertEquals(appConfigWrapper.getApp().getName(), "good_name");
    }

    @Test
    public void test_AppConfig_Mapping_Null_OAuth_ClientID() {

        String json = ResourceReader.readResource("mock_get_app_config_null_oauth_client_id_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapper.getMessage(), "child \"oauthClientId\" fails because [\"oauthClientId\" is required]");

        Assert.assertNotNull(errorWrapper.getInvalid());
        Assert.assertNotNull(errorWrapper.getInvalid().getOauthClientId());
        Assert.assertEquals(errorWrapper.getInvalid().getOauthClientId().getCode(), (Integer) 6);
        Assert.assertEquals(errorWrapper.getInvalid().getOauthClientId().getCodeMeaning(), "missing");
        Assert.assertEquals(errorWrapper.getInvalid().getOauthClientId().getMessage(), "\"oauthClientId\" is required");
    }

    @Test
    public void test_AppConfig_Mapping_Empty_OAuth_ClientID() {

        String json = ResourceReader.readResource("mock_get_app_config_empty_oauth_client_id_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapper.getMessage(), "child \"oauthClientId\" fails because [\"oauthClientId\" is not allowed to be empty]");

        Assert.assertNotNull(errorWrapper.getInvalid());
        Assert.assertNotNull(errorWrapper.getInvalid().getOauthClientId());
        Assert.assertEquals(errorWrapper.getInvalid().getOauthClientId().getCode(), (Integer) 7);
        Assert.assertEquals(errorWrapper.getInvalid().getOauthClientId().getCodeMeaning(), "invalidValue");
        Assert.assertEquals(errorWrapper.getInvalid().getOauthClientId().getMessage(), "\"oauthClientId\" is not allowed to be empty");
    }

    @Test
    public void test_AppConfig_Mapping_App_Not_Found() {

        String json = ResourceReader.readResource("mock_get_app_config_app_not_found_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 2);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "notFound");
        Assert.assertEquals(errorWrapper.getMessage(), "app not found.");
    }
}

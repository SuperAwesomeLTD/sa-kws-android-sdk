package kws.superawesome.tv.kwssdk.common.models.error;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.common.models.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.json.ParseJsonTask;

public class TestErrorMapping {

    @Test
    public void test_Error_Mapping_BadToken_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_invalid_token_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getErrorCode(), "invalid_token");
        Assert.assertEquals(errorWrapper.getError(), "The access token provided is invalid.");
    }

    @Test
    public void test_Error_Mapping_BadClientId_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_operation_not_supported_for_client_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 1);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "forbidden");
        Assert.assertEquals(errorWrapper.getMessage(), "operation not supported for this client");
    }

    @Test
    public void test_Error_Mapping_BadUserId_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_operation_not_supported_for_this_user_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 1);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "forbidden");
        Assert.assertEquals(errorWrapper.getMessage(), "operation not supported for this user");
    }

    //generic event not found

    @Test
    public void test_Error_Mapping_Event_Not_Found_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_event_not_found_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 2);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "notFound");
    }

}

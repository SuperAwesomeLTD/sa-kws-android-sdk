package kws.superawesome.tv.kwssdk.events.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestTriggerEventMapping {

    @Test
    public void test_TriggerEvent_Success_Response() {

        String json = ResourceReader.readResource("mock_generic_empty_response.json");

        Assert.assertEquals(json, "{}");
    }

    @Test
    public void test_TriggerEvent_Token_Reached_Limit_Response() {

        String json = ResourceReader.readResource("mock_trigger_event_token_reached_user_limit_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 1);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "forbidden");
        Assert.assertEquals(errorWrapperModel.getMessage(), "Token reached user limit");
    }

    @Test
    public void test_TriggerEvent_Token_Not_Valid_Response() {

        String json = ResourceReader.readResource("mock_trigger_event_token_not_valid_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"token\" fails because [\"token\" is not allowed to be empty]");
        Assert.assertNotNull(errorWrapperModel.getInvalid());
    }
}

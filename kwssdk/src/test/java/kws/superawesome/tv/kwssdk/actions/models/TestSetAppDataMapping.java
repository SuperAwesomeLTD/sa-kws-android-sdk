package kws.superawesome.tv.kwssdk.actions.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.common.models.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.json.ParseJsonTask;

public class TestSetAppDataMapping {

    @Test
    public void test_SetAppData_Mapping_Success_Response() {
        String json = ResourceReader.readResource("mock_generic_empty_response.json");

        Assert.assertNotNull(json);
        Assert.assertEquals(json, "{}");
    }

    @Test
    public void test_SetAppData_Mapping_Empty_Name() {
        String json = ResourceReader.readResource("mock_set_app_data_empty_name_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapper.getMessage(), "child \"name\" fails because [\"name\" is not allowed to be empty]");
    }

    @Test
    public void test_SetAppData_Mapping_Bad_Length_Name() {
        String json = ResourceReader.readResource("mock_set_app_name_len_bad_response.json");
        ErrorWrapperModel errorWrapper = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 11);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "unexpected");
        Assert.assertEquals(errorWrapper.getMessage(), "Validation error: Validation len on name failed");
    }
}

package kws.superawesome.tv.kwssdk.actions.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.common.models.error.ErrorWrapperModel;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestPermissionsMapping {

    @Test
    public void test_Permission_Mapping_Success_Response(){

        String json = ResourceReader.readResource("mock_generic_empty_response.json");

        Assert.assertEquals(json, "{}");
    }

    @Test
    public void test_Permission_Mapping_Empty_Permission_Response(){
        String json = ResourceReader.readResource("mock_request_permissions_empty_permission_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 5);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "validation");
        Assert.assertEquals(errorWrapperModel.getMessage(), "child \"permissions\" fails because [\"permissions\" at position 0 fails because [\"0\" is not allowed to be empty]]");
        Assert.assertNotNull(errorWrapperModel.getInvalid());

    }

    @Test
    public void test_Permission_Mapping_Permission_Not_Found_Response(){
        String json = ResourceReader.readResource("mock_request_permissions_not_found_response.json");
        ErrorWrapperModel errorWrapperModel = (ErrorWrapperModel) new ParseJsonTask(ErrorWrapperModel.class).execute(json).take();

        Assert.assertNotNull(errorWrapperModel);
        Assert.assertEquals(errorWrapperModel.getCode(), (Integer) 2);
        Assert.assertEquals(errorWrapperModel.getCodeMeaning(), "notFound");
        Assert.assertEquals(errorWrapperModel.getMessage(), "permissions not found: [permission_name]");
    }

}

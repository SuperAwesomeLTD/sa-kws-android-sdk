package kws.superawesome.tv.kwssdk.app.data.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.app.data.models.AppDataWrapperModel;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestGetAppDataMapping {

    @Test
    public void test_GetAppData_Mapping_Success_Response() {

        String json = ResourceReader.readResource("mock_get_app_data_success_response.json");
        AppDataWrapperModel appDataWrapperModel = (AppDataWrapperModel) new ParseJsonTask(AppDataWrapperModel.class).execute(json).take();

        Assert.assertNotNull(appDataWrapperModel);
        Assert.assertNotNull(appDataWrapperModel.getResults());
        Assert.assertEquals(appDataWrapperModel.getCount(), 4);
        Assert.assertEquals(appDataWrapperModel.getOffset(), 0);
        Assert.assertEquals(appDataWrapperModel.getLimit(), 1000);


        Assert.assertNotNull(appDataWrapperModel.getResults().get(0));
        Assert.assertEquals(appDataWrapperModel.getResults().get(0).getName(), "new_val");
        Assert.assertEquals(appDataWrapperModel.getResults().get(0).getValue(), 33);

        Assert.assertNotNull(appDataWrapperModel.getResults().get(1));
        Assert.assertEquals(appDataWrapperModel.getResults().get(1).getName(), "new_val_2");
        Assert.assertEquals(appDataWrapperModel.getResults().get(1).getValue(), 34);

        Assert.assertNotNull(appDataWrapperModel.getResults().get(2));
        Assert.assertEquals(appDataWrapperModel.getResults().get(2).getName(), "new_val_3");
        Assert.assertEquals(appDataWrapperModel.getResults().get(2).getValue(), -1);

        Assert.assertNotNull(appDataWrapperModel.getResults().get(3));
        Assert.assertEquals(appDataWrapperModel.getResults().get(3).getName(), "new_value_0");
        Assert.assertEquals(appDataWrapperModel.getResults().get(3).getValue(), 0);
    }


}

package kws.superawesome.tv.kwssdk.actions.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import tv.superawesome.samobilebase.json.ParseJsonTask;

public class TestHasTriggeredEventMapping {

    @Test
    public void test_HasTriggeredEvent_Success_Response() {

        String json = ResourceReader.readResource("mock_has_triggered_event_success_response.json");
        HasTriggeredEventModel hasTriggeredEventModel = (HasTriggeredEventModel) new ParseJsonTask(HasTriggeredEventModel.class).execute(json).take();

        Assert.assertNotNull(hasTriggeredEventModel);
        Assert.assertTrue(hasTriggeredEventModel.getHasTriggeredModel());
    }

}

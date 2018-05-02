package kws.superawesome.tv.kwssdk.scoring.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ResourceReader;
import kws.superawesome.tv.kwssdk.base.scoring.models.LeadersWrapperModel;
import tv.superawesome.samobilebase.json.ParseJsonTask;

public class TestLeadersMapping {

    @Test
    public void test_Scoring_GetLeader_Mapping_ResponseSuccess() {

        String json = ResourceReader.readResource("mock_get_leaders_success_response.json");
        LeadersWrapperModel leaders = (LeadersWrapperModel) new ParseJsonTask(LeadersWrapperModel.class).execute(json).take();

        Assert.assertNotNull(leaders);
        Assert.assertNotNull(leaders.getResults());

        Assert.assertEquals(leaders.getCount(), 2);
        Assert.assertEquals(leaders.getOffset(), 0);
        Assert.assertEquals(leaders.getLimit(), 1000);

        Assert.assertNotNull(leaders.getResults().get(0));
        Assert.assertEquals(leaders.getResults().get(0).getName(), "testuser9112");
        Assert.assertEquals(leaders.getResults().get(0).getScore(), 540);
        Assert.assertEquals(leaders.getResults().get(0).getRank(), 1);

        Assert.assertNotNull(leaders.getResults().get(1));
        Assert.assertEquals(leaders.getResults().get(1).getName(), "testusr472");
        Assert.assertEquals(leaders.getResults().get(1).getScore(), 40);
        Assert.assertEquals(leaders.getResults().get(1).getRank(), 2);

    }
}

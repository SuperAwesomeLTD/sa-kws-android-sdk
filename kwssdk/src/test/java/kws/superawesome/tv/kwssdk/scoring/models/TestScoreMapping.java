package kws.superawesome.tv.kwssdk.scoring.models;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.base.error.ErrorWrapperModel;
import kws.superawesome.tv.kwssdk.base.scoring.models.ScoreModel;
import kws.superawesome.tv.kwssdk.ResourceReader;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestScoreMapping {

    @Test
    public void test_Scoring_GetScore_Mapping_ResponseSuccess() {

        String json = ResourceReader.readResource("mock_get_user_score_success_response.json");
        ScoreModel scoreResponse = (ScoreModel) new ParseJsonTask(ScoreModel.class).execute(json).take();

        Assert.assertNotNull(scoreResponse);
        Assert.assertEquals(scoreResponse.getScore(), 600);
        Assert.assertEquals(scoreResponse.getRank(), 1);
    }
}

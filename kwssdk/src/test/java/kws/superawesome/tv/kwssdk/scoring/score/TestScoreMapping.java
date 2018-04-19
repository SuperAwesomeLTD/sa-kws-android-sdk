package kws.superawesome.tv.kwssdk.scoring.score;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.base.error.ErrorWrapper;
import kws.superawesome.tv.kwssdk.base.scoring.models.ScoreModel;
import kws.superawesome.tv.kwssdk.services.ResourceReader;
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

    @Test
    public void test_Scoring_GetScore_Mapping_BadToken_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_invalid_token_response.json");
        ErrorWrapper errorWrapper = (ErrorWrapper) new ParseJsonTask(ErrorWrapper.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getErrorCode(),  "invalid_token");
        Assert.assertEquals(errorWrapper.getError(), "The access token provided is invalid.");
    }

    @Test
    public void test_Scoring_GetScore_Mapping_BadClientId_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_operation_not_supported_for_client_response.json");
        ErrorWrapper errorWrapper = (ErrorWrapper) new ParseJsonTask(ErrorWrapper.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 1);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "forbidden");
        Assert.assertEquals(errorWrapper.getMessage(), "operation not supported for this client");
    }
}

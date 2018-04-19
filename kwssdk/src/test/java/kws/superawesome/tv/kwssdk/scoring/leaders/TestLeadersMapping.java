package kws.superawesome.tv.kwssdk.scoring.leaders;

import org.junit.Assert;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.base.error.ErrorWrapper;
import kws.superawesome.tv.kwssdk.base.scoring.models.LeadersWrapper;
import kws.superawesome.tv.kwssdk.services.ResourceReader;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

public class TestLeadersMapping {


    @Test
    public void test_Scoring_GetLeader_Mapping_ResponseSuccess() {

        String json = ResourceReader.readResource("mock_get_leaders_success_response.json");
        LeadersWrapper leaders = (LeadersWrapper) new ParseJsonTask(LeadersWrapper.class).execute(json).take();

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

    @Test
    public void test_Scoring_GetLeaders_Mapping_BadToken_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_invalid_token_response.json");
        ErrorWrapper errorWrapper = (ErrorWrapper) new ParseJsonTask(ErrorWrapper.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getErrorCode(), "invalid_token");
        Assert.assertEquals(errorWrapper.getError(), "The access token provided is invalid.");
    }

    @Test
    public void test_Scoring_GetLeaders_Mapping_BadClientId_Response() {

        //401
        String json = ResourceReader.readResource("mock_generic_operation_not_supported_for_client_response.json");
        ErrorWrapper errorWrapper = (ErrorWrapper) new ParseJsonTask(ErrorWrapper.class).execute(json).take();

        Assert.assertNotNull(errorWrapper);

        Assert.assertEquals(errorWrapper.getCode(), (Integer) 1);
        Assert.assertEquals(errorWrapper.getCodeMeaning(), "forbidden");
        Assert.assertEquals(errorWrapper.getMessage(), "operation not supported for this client");
    }
}

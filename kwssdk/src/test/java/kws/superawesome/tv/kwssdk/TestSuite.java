package kws.superawesome.tv.kwssdk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import kws.superawesome.tv.kwssdk.TestRequests.TestAppConfigRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestCreateUserRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestGetAppDataRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestGetTempAccessTokenRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestHasTriggeredEventRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestInviteUserRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestLeadersRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestLoginRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestPermissionsRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestRandomUsernameRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestScoreRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestSetAppDataRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestTriggerEventRequest;
import kws.superawesome.tv.kwssdk.TestRequests.TestUserDetailsRequest;

/**
 * Created by guilherme.mota on 09/01/2018.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        //Requests
        TestAppConfigRequest.class,
        TestCreateUserRequest.class,
        TestGetAppDataRequest.class,
        TestGetTempAccessTokenRequest.class,
        TestHasTriggeredEventRequest.class,
        TestInviteUserRequest.class,
        TestLeadersRequest.class,
        TestLoginRequest.class,
        TestPermissionsRequest.class,
        TestRandomUsernameRequest.class,
        TestScoreRequest.class,
        TestSetAppDataRequest.class,
        TestTriggerEventRequest.class,
        TestUserDetailsRequest.class
})
public class TestSuite {
}
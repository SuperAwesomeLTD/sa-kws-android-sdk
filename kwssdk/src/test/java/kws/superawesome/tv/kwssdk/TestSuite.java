package kws.superawesome.tv.kwssdk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import kws.superawesome.tv.kwssdk.providers.TestAppProviderTest;
import kws.superawesome.tv.kwssdk.providers.TestCreateUserProviderTest;
import kws.superawesome.tv.kwssdk.providers.TestLoginProviderTest;
import kws.superawesome.tv.kwssdk.requests.TestAppConfigRequest;
import kws.superawesome.tv.kwssdk.requests.TestCreateUserRequest;
import kws.superawesome.tv.kwssdk.requests.TestGetAppDataRequest;
import kws.superawesome.tv.kwssdk.requests.TestGetTempAccessTokenRequest;
import kws.superawesome.tv.kwssdk.requests.TestHasTriggeredEventRequest;
import kws.superawesome.tv.kwssdk.requests.TestInviteUserRequest;
import kws.superawesome.tv.kwssdk.requests.TestLeadersRequest;
import kws.superawesome.tv.kwssdk.requests.TestLoginRequest;
import kws.superawesome.tv.kwssdk.requests.TestPermissionsRequest;
import kws.superawesome.tv.kwssdk.requests.TestRandomUsernameRequest;
import kws.superawesome.tv.kwssdk.requests.TestScoreRequest;
import kws.superawesome.tv.kwssdk.requests.TestSetAppDataRequest;
import kws.superawesome.tv.kwssdk.requests.TestTriggerEventRequest;
import kws.superawesome.tv.kwssdk.requests.TestUserDetailsRequest;

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
        TestUserDetailsRequest.class,

        //Providers
        TestLoginProviderTest.class,
        TestAppProviderTest.class,
        TestCreateUserProviderTest.class


})
public class TestSuite {
}
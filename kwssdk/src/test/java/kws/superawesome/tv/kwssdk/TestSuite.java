package kws.superawesome.tv.kwssdk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import kws.superawesome.tv.kwssdk.requests.TestAppConfigWrapperRequest;
import kws.superawesome.tv.kwssdk.requests.TestAuthUserResponseRequest;
import kws.superawesome.tv.kwssdk.requests.TestGetAppDataWrapperRequest;
import kws.superawesome.tv.kwssdk.requests.TestGetTempAccessTokenRequest;
import kws.superawesome.tv.kwssdk.requests.TestHasTriggeredEventRequest;
import kws.superawesome.tv.kwssdk.requests.TestInviteUserRequest;
import kws.superawesome.tv.kwssdk.requests.TestLeadersWrapperRequest;
import kws.superawesome.tv.kwssdk.requests.TestLoginAuthResponseRequest;
import kws.superawesome.tv.kwssdk.requests.TestOAuthUserTokenRequest;
import kws.superawesome.tv.kwssdk.requests.TestPermissionsRequest;
import kws.superawesome.tv.kwssdk.requests.TestRandomUsernameRequest;
import kws.superawesome.tv.kwssdk.requests.TestScoreRequest;
import kws.superawesome.tv.kwssdk.requests.TestSetAppDataWrapperRequest;
import kws.superawesome.tv.kwssdk.requests.TestTriggerEventRequest;
import kws.superawesome.tv.kwssdk.requests.TestUserDetailsRequest;
import kws.superawesome.tv.kwssdk.services.odlServices.app.TestAppService_GetAppDataWrapper;
import kws.superawesome.tv.kwssdk.services.odlServices.app.TestAppService_GetLeadersWrapper;
import kws.superawesome.tv.kwssdk.services.odlServices.app.TestAppService_SetAppDataWrapper;
import kws.superawesome.tv.kwssdk.services.odlServices.create_user.TestAuthUserResponseService;
import kws.superawesome.tv.kwssdk.services.odlServices.events.TestEventsService_HasTriggeredEvent;
import kws.superawesome.tv.kwssdk.services.odlServices.events.TestEventsService_TriggerEvent;
import kws.superawesome.tv.kwssdk.services.odlServices.login.TestLoginService_LoginAuthResponse;
import kws.superawesome.tv.kwssdk.services.odlServices.random_username.TestRandomUsernameService;
import kws.superawesome.tv.kwssdk.services.odlServices.user.TestUserService_GetScore;
import kws.superawesome.tv.kwssdk.services.odlServices.user.TestUserService_GetUserDetails;
import kws.superawesome.tv.kwssdk.services.odlServices.user.TestUserService_InviteUser;
import kws.superawesome.tv.kwssdk.services.odlServices.user.TestUserService_RequestPermissions;

/**
 * Created by guilherme.mota on 09/01/2018.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        //Requests
        TestAppConfigWrapperRequest.class,
        TestAuthUserResponseRequest.class,
        TestGetAppDataWrapperRequest.class,
        TestGetTempAccessTokenRequest.class,
        TestHasTriggeredEventRequest.class,
        TestInviteUserRequest.class,
        TestLeadersWrapperRequest.class,
        TestLoginAuthResponseRequest.class,
        TestPermissionsRequest.class,
        TestRandomUsernameRequest.class,
        TestScoreRequest.class,
        TestSetAppDataWrapperRequest.class,
        TestTriggerEventRequest.class,
        TestUserDetailsRequest.class,
        TestOAuthUserTokenRequest.class,

        //Services
        //App
        TestAppService_GetAppDataWrapper.class,
        TestAppService_GetLeadersWrapper.class,
        TestAppService_SetAppDataWrapper.class,

        //LoginAuthResponse
        TestLoginService_LoginAuthResponse.class,

        //Create User
        TestAuthUserResponseService.class,

        //Events
        TestEventsService_TriggerEvent.class,
        TestEventsService_HasTriggeredEvent.class,

        //Random Username
        TestRandomUsernameService.class,

        //User
        TestUserService_GetScore.class,
        TestUserService_GetUserDetails.class,
        TestUserService_InviteUser.class,
        TestUserService_RequestPermissions.class


})
public class TestSuite {
}
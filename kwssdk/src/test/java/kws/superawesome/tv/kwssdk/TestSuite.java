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
import kws.superawesome.tv.kwssdk.requests.TestGetUserDetailsRequest;
import kws.superawesome.tv.kwssdk.services.auth.TestAuthService_CreateUser;
import kws.superawesome.tv.kwssdk.services.auth.TestAuthService_Login;
import kws.superawesome.tv.kwssdk.services.scoring.TestScoringService_GetLeaderboard;
import kws.superawesome.tv.kwssdk.services.scoring.TestScoringService_GetScore;
import kws.superawesome.tv.kwssdk.services.session.TestSessionService_GetCurrentUser;
import kws.superawesome.tv.kwssdk.services.session.TestSessionService_IsUserLoggedIn;
import kws.superawesome.tv.kwssdk.services.session.TestSessionService_SaveLoggedUser;
import kws.superawesome.tv.kwssdk.services.user.TestUserService_GetUserDetails;
import kws.superawesome.tv.kwssdk.services.user.TestUserService_UpdateUserDetails;
import kws.superawesome.tv.kwssdk.services.useractions.TestUserActionsService_GetAppData;
import kws.superawesome.tv.kwssdk.services.useractions.TestUserActionsService_HasTriggeredEvent;
import kws.superawesome.tv.kwssdk.services.useractions.TestUserActionsService_InviteUser;
import kws.superawesome.tv.kwssdk.services.useractions.TestUserActionsService_RequestPermissions;
import kws.superawesome.tv.kwssdk.services.useractions.TestUserActionsService_SetAppData;
import kws.superawesome.tv.kwssdk.services.useractions.TestUserActionsService_TriggerEvent;
import kws.superawesome.tv.kwssdk.services.username.TestRandomUsernameService;

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
        TestGetUserDetailsRequest.class,
        TestOAuthUserTokenRequest.class,

        //Services

        //Auth
        TestAuthService_Login.class,
        TestAuthService_CreateUser.class,

        //Scoring
        TestScoringService_GetLeaderboard.class,
        TestScoringService_GetScore.class,

        //Session
        TestSessionService_GetCurrentUser.class,
        TestSessionService_IsUserLoggedIn.class,
        TestSessionService_SaveLoggedUser.class,

        //User
        TestUserService_GetUserDetails.class,
        TestUserService_UpdateUserDetails.class,

        //User Actions
        TestUserActionsService_GetAppData.class,
        TestUserActionsService_SetAppData.class,
        TestUserActionsService_TriggerEvent.class,
        TestUserActionsService_HasTriggeredEvent.class,
        TestUserActionsService_InviteUser.class,
        TestUserActionsService_RequestPermissions.class,

        //Username
        TestRandomUsernameService.class


})
public class TestSuite {
}
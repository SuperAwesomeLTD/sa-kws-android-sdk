package kws.superawesome.tv.kwssdk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import kws.superawesome.tv.kwssdk.app_config.models.TestAppConfigMapping;
import kws.superawesome.tv.kwssdk.app_config.requests.TestAppConfigRequest;
import kws.superawesome.tv.kwssdk.app_data.models.TestGetAppDataMapping;
import kws.superawesome.tv.kwssdk.app_data.models.TestSetAppDataMapping;
import kws.superawesome.tv.kwssdk.app_data.requests.TestGetAppDataRequest;
import kws.superawesome.tv.kwssdk.app_data.requests.TestSetAppDataRequest;
import kws.superawesome.tv.kwssdk.authentication.models.TestAuthUserResponseModel;
import kws.superawesome.tv.kwssdk.authentication.models.TestLoginAuthResponseModel;
import kws.superawesome.tv.kwssdk.authentication.requests.TestCreateUserRequest;
import kws.superawesome.tv.kwssdk.authentication.requests.TestLoginRequest;
import kws.superawesome.tv.kwssdk.authentication.requests.TestOAuthUserTokenRequest;
import kws.superawesome.tv.kwssdk.authentication.services.TestAuthService;
import kws.superawesome.tv.kwssdk.authentication.services.TestAuthService_CreateUser;
import kws.superawesome.tv.kwssdk.authentication.services.TestAuthService_Login;
import kws.superawesome.tv.kwssdk.error.models.TestErrorMapping;
import kws.superawesome.tv.kwssdk.events.models.TestHasTriggeredEventMapping;
import kws.superawesome.tv.kwssdk.events.models.TestTriggerEventMapping;
import kws.superawesome.tv.kwssdk.events.requests.TestHasTriggeredEventRequest;
import kws.superawesome.tv.kwssdk.events.requests.TestTriggerEventRequest;
import kws.superawesome.tv.kwssdk.invite_user.models.TestInviteUserMapping;
import kws.superawesome.tv.kwssdk.invite_user.requests.TestInviteUserRequest;
import kws.superawesome.tv.kwssdk.permissions.models.TestPermissionsMapping;
import kws.superawesome.tv.kwssdk.permissions.requests.TestPermissionsRequest;
import kws.superawesome.tv.kwssdk.scoring.models.TestLeadersMapping;
import kws.superawesome.tv.kwssdk.scoring.models.TestScoreMapping;
import kws.superawesome.tv.kwssdk.scoring.requests.TestLeadersRequest;
import kws.superawesome.tv.kwssdk.scoring.requests.TestScoreRequest;
import kws.superawesome.tv.kwssdk.scoring.services.TestScoringService;
import kws.superawesome.tv.kwssdk.scoring.services.TestScoringService_GetLeaderboard;
import kws.superawesome.tv.kwssdk.scoring.services.TestScoringService_GetScore;
import kws.superawesome.tv.kwssdk.session.services.TestSessionService_GetCurrentUser;
import kws.superawesome.tv.kwssdk.session.services.TestSessionService_IsUserLoggedIn;
import kws.superawesome.tv.kwssdk.session.services.TestSessionService_SaveLoggedUser;
import kws.superawesome.tv.kwssdk.temp_access_token.requests.TestGetTempAccessTokenRequest;
import kws.superawesome.tv.kwssdk.user.models.TestGetUserDetailsMapping;
import kws.superawesome.tv.kwssdk.user.models.TestUpdateUserDetailsMapping;
import kws.superawesome.tv.kwssdk.user.requests.TestGetUserDetailsRequest;
import kws.superawesome.tv.kwssdk.user.requests.TestUpdateGetUserDetailsRequest;
import kws.superawesome.tv.kwssdk.user.services.TestUserService;
import kws.superawesome.tv.kwssdk.user.services.TestUserService_GetUserDetails;
import kws.superawesome.tv.kwssdk.user.services.TestUserService_UpdateUserDetails;
import kws.superawesome.tv.kwssdk.user_actions.services.TestUserActionsService;
import kws.superawesome.tv.kwssdk.user_actions.services.TestUserActionsService_GetAppData;
import kws.superawesome.tv.kwssdk.user_actions.services.TestUserActionsService_HasTriggeredEvent;
import kws.superawesome.tv.kwssdk.user_actions.services.TestUserActionsService_InviteUser;
import kws.superawesome.tv.kwssdk.user_actions.services.TestUserActionsService_RequestPermissions;
import kws.superawesome.tv.kwssdk.user_actions.services.TestUserActionsService_SetAppData;
import kws.superawesome.tv.kwssdk.user_actions.services.TestUserActionsService_TriggerEvent;
import kws.superawesome.tv.kwssdk.username.models.TestRandomUsernameMapping;
import kws.superawesome.tv.kwssdk.username.services.TestUsernameService_RandomUsername;
import kws.superawesome.tv.kwssdk.username.requests.TestRandomUsernameRequest;

/**
 * Created by guilherme.mota on 09/01/2018.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({

        ////////////////////////////////////////////////////////////////////////////////////////////
        // REQUESTS
        ////////////////////////////////////////////////////////////////////////////////////////////

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
        TestGetUserDetailsRequest.class,
        TestOAuthUserTokenRequest.class,
        TestUpdateGetUserDetailsRequest.class,

        ////////////////////////////////////////////////////////////////////////////////////////////
        // SERVICES
        ////////////////////////////////////////////////////////////////////////////////////////////

        //Auth
        TestAuthService.class,
        TestAuthService_Login.class,
        TestAuthService_CreateUser.class,

        //Scoring
        TestScoringService.class,
        TestScoringService_GetLeaderboard.class,
        TestScoringService_GetScore.class,

        //Session
        TestSessionService_GetCurrentUser.class,
        TestSessionService_IsUserLoggedIn.class,
        TestSessionService_SaveLoggedUser.class,

        //User
        TestUserService.class,
        TestUserService_GetUserDetails.class,
        TestUserService_UpdateUserDetails.class,

        //User Actions
        TestUserActionsService.class,
        TestUserActionsService_GetAppData.class,
        TestUserActionsService_HasTriggeredEvent.class,
        TestUserActionsService_InviteUser.class,
        TestUserActionsService_RequestPermissions.class,
        TestUserActionsService_SetAppData.class,
        TestUserActionsService_TriggerEvent.class,

        //Username
        TestUsernameService_RandomUsername.class,

        ////////////////////////////////////////////////////////////////////////////////////////////
        // MODELS
        ////////////////////////////////////////////////////////////////////////////////////////////

        //App Config
        TestAppConfigMapping.class,

        //App Data
        TestSetAppDataMapping.class,
        TestGetAppDataMapping.class,

        //Authentication
        TestAuthUserResponseModel.class,
        TestLoginAuthResponseModel.class,

        //Scoring
        TestLeadersMapping.class,
        TestScoreMapping.class,

        //Events
        TestHasTriggeredEventMapping.class,
        TestTriggerEventMapping.class,

        //User
        TestGetUserDetailsMapping.class,
        TestUpdateUserDetailsMapping.class,

        //Permissions
        TestPermissionsMapping.class,

        //Invite User
        TestInviteUserMapping.class,

        //Random Username
        TestRandomUsernameMapping.class,

        //Error
        TestErrorMapping.class


})
public class TestSuite {
}
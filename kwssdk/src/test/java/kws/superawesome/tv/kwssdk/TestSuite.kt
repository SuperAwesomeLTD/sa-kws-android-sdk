package kws.superawesome.tv.kwssdk

import kws.superawesome.tv.kwssdk.TestRequests.kotlin.*
import kws.superawesome.tv.kwssdk.TestResponses.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by guilherme.mota on 14/12/2017.
 */

@RunWith(Suite::class)
@Suite.SuiteClasses
(
        //Requests
        KWS_BaseRequest_Test::class,
        KWS_CreateUserRequest_Test::class,
        KWS_LoginUserRequest_Test::class,
        KWS_TempAccessTokenRequest_Test::class,
        KWS_AppConfigRequest_Test::class,
        KWS_RandomUsernameRequest_Test::class,
        KWS_UserDetailsRequest_Test::class,
        KWS_InviteUserRequest_Test::class,
        KWS_TriggerEventRequest_Test::class,
        KWS_HasTriggeredEventRequest_Test::class,
        KWS_LeadersRequest_Test::class,
        KWS_ScoreRequest_Test::class,
        KWS_SetAppDataRequest_Test::class,
        KWS_GetAppDataRequest_Test::class,
        KWS_PermissionsRequest_Test::class,

        //Responses
        KWS_CreateUserResponse_Test::class,
        KWS_LoginResponse_Test::class,
        KWS_RandomUsernameResponse_Test::class,
        KWS_AppConfigResponse_Test::class,
        KWS_UserDetailsResponse_Test::class,
        KWS_LeadersResponse_Test::class,
        KWS_ScoreResponse_Test::class,
        KWS_GetAppDataResponse_Test::class
)

class TestSuite {
}

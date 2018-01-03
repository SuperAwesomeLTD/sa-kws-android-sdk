package kws.superawesome.tv.kwssdk

import kws.superawesome.tv.kwssdk.TestRequests.*
import kws.superawesome.tv.kwssdk.TestResponses.KWS_AppConfigResponse_Test
import kws.superawesome.tv.kwssdk.TestResponses.KWS_CreateUserResponse_Test
import kws.superawesome.tv.kwssdk.TestResponses.KWS_GetRandomUsernameResponse_Test
import kws.superawesome.tv.kwssdk.TestResponses.KWS_LoginResponse_Test
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
        KWS_GetTempAccessTokenRequest_Test::class,
        KWS_GetAppConfigRequest_Test::class,
        KWS_GetRandomUsernameRequest_Test::class,

        //Responses
        KWS_CreateUserResponse_Test::class,
        KWS_LoginResponse_Test::class,
        KWS_GetRandomUsernameResponse_Test::class,
        KWS_AppConfigResponse_Test::class
)

class TestSuite {
}
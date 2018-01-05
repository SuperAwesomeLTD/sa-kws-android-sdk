package kws.superawesome.tv.kwssdk

import kws.superawesome.tv.kwssdk.TestRequests.*
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

        //Responses
        KWS_CreateUserResponse_Test::class,
        KWS_LoginResponse_Test::class,
        KWS_RandomUsernameResponse_Test::class,
        KWS_AppConfig_Test::class,
        KWS_UserDetailsResponse_Test::class
)

class TestSuite {
}
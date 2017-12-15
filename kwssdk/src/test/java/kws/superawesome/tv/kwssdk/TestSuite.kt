package kws.superawesome.tv.kwssdk

import kws.superawesome.tv.kwssdk.TestRequests.KWS_BaseRequest_Test
import kws.superawesome.tv.kwssdk.TestRequests.KWS_CreateUserRequest_Test
import kws.superawesome.tv.kwssdk.TestRequests.KWS_GetTempAccessTokenRequest_Test
import kws.superawesome.tv.kwssdk.TestRequests.KWS_LoginUserRequest_Test
import kws.superawesome.tv.kwssdk.TestResponses.KWS_CreateUserResponse_Test
import kws.superawesome.tv.kwssdk.TestResponses.KWS_LoginResponse_Test
import kws.superawesome.tv.kwssdk.kotlin.KWS_LoggedUser_Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by guilherme.mota on 14/12/2017.
 */

@RunWith(Suite::class)
@Suite.SuiteClasses
(
        KWS_BaseRequest_Test::class,
        KWS_CreateUserRequest_Test::class,
        KWS_LoginUserRequest_Test::class,
        KWS_GetTempAccessTokenRequest_Test::class,
        KWS_CreateUserResponse_Test::class,
        KWS_LoginResponse_Test::class,
        KWS_LoggedUser_Test::class
)

class TestSuite {
}
package kws.superawesome.tv.kwssdk.TestRequests.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.InviteUserRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsMapContaining
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Created by guilherme.mota on 04/01/2018.
 */
class KWS_InviteUserRequest_Test {

    private val APPID = "stan-test" // "superawesomeclub";
    private val KEY = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4"
    private val URL = "https://stan-test-cluster.api.kws.superawesome.tv/"


    val kwsNetworkEnvironmnet = object : KWSNetworkEnvironment {
        override val appID: String
            get() = APPID

        override val mobileKey: String
            get() = KEY

        override val domain: String
            get() = URL
    }

    @Test
    fun GetUserDetailsRequest_Valid() {


        val userId = 25
        val endpoint = "v1/users/$userId/invite-user"
        val method = NetworkMethod.POST
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc" +
                "3QiLCJzY29wZSI6Im1vYmlsZUFwcCIsImlhdCI6MTUxMzM1MDM0NywiZXhwIjoxNTEzNDM2NzQ3LCJpc3Mi" +
                "OiJzdXBlcmF3ZXNvbWUifQ.zBLzyW3RJC7ybZyOeKq5SLulq6L1SmIpdDSByR1rLAc"
        val emailAddress = "guilherme.mota+4@superawesome.tv"


        //when
        val inviteUserRequest = InviteUserRequest(
                environment = kwsNetworkEnvironmnet,
                userId = userId,
                token = token,
                emailAddress = emailAddress
        )


        //then
        assertNotNull(inviteUserRequest)

        //headers
        MatcherAssert.assertThat<Map<String, String>>(inviteUserRequest.headers,
                IsMapContaining.hasEntry("Content-Type", "application/json"))

        //endpoint
        assertEquals(inviteUserRequest.endpoint, endpoint)

        //method type request
        assertEquals(inviteUserRequest.method, method)

        //query
        assertNull(inviteUserRequest.query)

        //body
        MatcherAssert.assertThat(inviteUserRequest.body!!.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(inviteUserRequest.body, IsMapContaining.hasKey("email"))

        //form encoded urls
        assertFalse(inviteUserRequest.formEncodeUrls)


    }


}
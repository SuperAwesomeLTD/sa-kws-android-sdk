package kws.superawesome.tv.kwssdk.TestRequests.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.TriggerEventRequest
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 04/01/2018.
 */
class KWS_TriggerEventRequest_Test {

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
        val endpoint = "v1/users/$userId/trigger-event"
        val method = NetworkMethod.POST
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc" +
                "3QiLCJzY29wZSI6Im1vYmlsZUFwcCIsImlhdCI6MTUxMzM1MDM0NywiZXhwIjoxNTEzNDM2NzQ3LCJpc3Mi" +
                "OiJzdXBlcmF3ZXNvbWUifQ.zBLzyW3RJC7ybZyOeKq5SLulq6L1SmIpdDSByR1rLAc"

        val points = 20
        val eventToken = "a7tzV7QLhlR0rS8KK98QcZgrQk3ur260"


        //when
        val triggerEventRequest = TriggerEventRequest(
                environment = kwsNetworkEnvironmnet,
                points = points,
                userId = userId,
                token = token,
                eventToken = eventToken
        )


        //then
//        assertNotNull(triggerEventRequest)
//
//        //headers
//        assertThat<Map<String, String>>(triggerEventRequest.headers,
//                IsMapContaining.hasEntry("Content-Type", "application/json"))
//
//        //endpoint
//        assertEquals(triggerEventRequest.endpoint, endpoint)
//
//        //method type request
//        assertEquals(triggerEventRequest.method, method)
//
//        //query
//        assertNull(triggerEventRequest.query)
//
//        //body
//        MatcherAssert.assertThat(triggerEventRequest.body!!.size, CoreMatchers.`is`(2))
//        MatcherAssert.assertThat(triggerEventRequest.body, IsMapContaining.hasKey("points"))
//        MatcherAssert.assertThat(triggerEventRequest.body, IsMapContaining.hasKey("token"))
//
//        //form encoded urls
//        assertFalse(triggerEventRequest.formEncodeUrls)


    }


}
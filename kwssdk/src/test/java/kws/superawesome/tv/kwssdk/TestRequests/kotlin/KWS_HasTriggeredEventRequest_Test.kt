package kws.superawesome.tv.kwssdk.TestRequests.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.HasTriggeredEventRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 05/01/2018.
 */

@Deprecated("Not used anymore")
class KWS_HasTriggeredEventRequest_Test {

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
        val endpoint = "v1/users/$userId/has-triggered-event"
        val method = NetworkMethod.POST
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc" +
                "3QiLCJzY29wZSI6Im1vYmlsZUFwcCIsImlhdCI6MTUxMzM1MDM0NywiZXhwIjoxNTEzNDM2NzQ3LCJpc3Mi" +
                "OiJzdXBlcmF3ZXNvbWUifQ.zBLzyW3RJC7ybZyOeKq5SLulq6L1SmIpdDSByR1rLAc"

        val eventId = 10


        //when
        val hasTriggeredEventRequest = HasTriggeredEventRequest(
                environment = kwsNetworkEnvironmnet,
                userId = userId,
                eventId = eventId,
                token = token
        )


//        //then
//        assertNotNull(hasTriggeredEventRequest)
//
//        //headers
//        assertThat<Map<String, String>>(hasTriggeredEventRequest.headers,
//                IsMapContaining.hasEntry("Content-Type", "application/json"))
//
//        //endpoint
//        assertEquals(hasTriggeredEventRequest.endpoint, endpoint)
//
//        //method type request
//        assertEquals(hasTriggeredEventRequest.method, method)
//
//        //query
//        assertNull(hasTriggeredEventRequest.query)
//
//        //body
//        MatcherAssert.assertThat(hasTriggeredEventRequest.body!!.size, CoreMatchers.`is`(1))
//        MatcherAssert.assertThat(hasTriggeredEventRequest.body, IsMapContaining.hasKey("eventId"))
//
//        //form encoded urls
//        assertFalse(hasTriggeredEventRequest.formEncodeUrls)


    }


}
package kws.superawesome.tv.kwssdk.TestRequests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.LeadersRequest
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsMapContaining
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Created by guilherme.mota on 05/01/2018.
 */
class KWS_LeadersRequest_Test {
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
    fun GetLeadersRequest_Valid() {


        val appId = 2
        val endpoint = "v1/apps/$appId/leaders"
        val method = NetworkMethod.GET
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc" +
                "3QiLCJzY29wZSI6Im1vYmlsZUFwcCIsImlhdCI6MTUxMzM1MDM0NywiZXhwIjoxNTEzNDM2NzQ3LCJpc3Mi" +
                "OiJzdXBlcmF3ZXNvbWUifQ.zBLzyW3RJC7ybZyOeKq5SLulq6L1SmIpdDSByR1rLAc"


        //when
        val getLeadersRequest = LeadersRequest(
                environment = kwsNetworkEnvironmnet,
                appId = appId,
                token = token
        )


        //then
        assertNotNull(getLeadersRequest)

        //headers
        MatcherAssert.assertThat<Map<String, String>>(getLeadersRequest.headers,
                IsMapContaining.hasEntry("Content-Type", "application/json"))

        //endpoint
        assertEquals(getLeadersRequest.endpoint, endpoint)

        //method type request
        assertEquals(getLeadersRequest.method, method)

        //query
        assertNull(getLeadersRequest.query)

        //body
        assertNull(getLeadersRequest.body)

        //form encoded urls
        assertFalse(getLeadersRequest.formEncodeUrls)


    }
}
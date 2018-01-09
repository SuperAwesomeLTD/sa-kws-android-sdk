package kws.superawesome.tv.kwssdk.TestRequests.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 15/12/2017.
 */
class KWS_TempAccessTokenRequest_Test {


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
    fun GetTempAccessTokenRequest_Valid() {

        //given
        val endpoint = "oauth/token"
        val method = NetworkMethod.POST


        //when
        val getTempAccessTokenRequest = TempAccessTokenRequest(
                environment = kwsNetworkEnvironmnet,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
//        assertNotNull(getTempAccessTokenRequest)
//
//        //headers
//        assertThat<Map<String, String>>(getTempAccessTokenRequest.headers as Map<String, String>?,
//                IsMapContaining.hasEntry("Content-Type", "application/x-www-form-urlencoded"))
//
//        //endpoint
//        assertEquals(getTempAccessTokenRequest.endpoint, endpoint)
//
//        //method type request
//        assertEquals(getTempAccessTokenRequest.method, method)
//
//        //query
//        assertNull(getTempAccessTokenRequest.query)
//
//        //body
//        assertThat(getTempAccessTokenRequest.body!!.size, CoreMatchers.`is`(3))
//        assertThat<Map<String, String>>(getTempAccessTokenRequest.body as Map<String, String>?, IsMapContaining.hasEntry("grant_type", "client_credentials"))
//        assertThat(getTempAccessTokenRequest.body, IsMapContaining.hasKey("client_id"))
//        assertThat(getTempAccessTokenRequest.body, IsMapContaining.hasKey("client_secret"))
//
//        //form encoded urls
//        assertTrue(getTempAccessTokenRequest.formEncodeUrls)

    }


}
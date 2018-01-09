package kws.superawesome.tv.kwssdk.TestRequests.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.RandomUsernameRequest
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 29/12/2017.
 */
class KWS_RandomUsernameRequest_Test {
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
    fun GetAppConfigRequest_Valid() {

        //given
        val appID = 2
        val endpoint = "v2/apps/$appID/random-display-name"
        val method = NetworkMethod.GET

        //when
        val getRandomUsernameRequest = RandomUsernameRequest(
                environment = kwsNetworkEnvironmnet,
                appID = appID)


//        //then
//        assertNotNull(getRandomUsernameRequest)
//
//        //headers
//        assertThat<Map<String, String>>(getRandomUsernameRequest.headers,
//                IsMapContaining.hasEntry("Content-Type", "application/json"))
//
//        //endpoint
//        assertEquals(getRandomUsernameRequest.endpoint, endpoint)
//
//        //method type
//        assertEquals(getRandomUsernameRequest.method, method)
//
//        //query
//        assertNull(getRandomUsernameRequest.query)
//
//        //body
//        assertNull(getRandomUsernameRequest.body)
//
//        //form encoded urls
//        assertFalse(getRandomUsernameRequest.formEncodeUrls)
    }
}
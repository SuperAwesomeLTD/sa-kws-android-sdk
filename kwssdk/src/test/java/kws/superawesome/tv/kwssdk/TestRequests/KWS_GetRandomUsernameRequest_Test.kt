package kws.superawesome.tv.kwssdk.TestRequests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.GetRandomUsernameRequest
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod
import kotlin.test.*

/**
 * Created by guilherme.mota on 29/12/2017.
 */
class KWS_GetRandomUsernameRequest_Test {
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
        val getRandomUsernameRequest = GetRandomUsernameRequest(
                environment = kwsNetworkEnvironmnet,
                appID = appID)


        //then
        assertNotNull(getRandomUsernameRequest)

        //headers
        assertThat<Map<String, String>>(getRandomUsernameRequest.headers,
                IsMapContaining.hasEntry("Content-Type", "application/json"))

        //endpoint
        assertEquals(getRandomUsernameRequest.endpoint, endpoint)

        //method type
        assertEquals(getRandomUsernameRequest.method, method)

        //query
        assertNull(getRandomUsernameRequest.query)

        //body
        assertNull(getRandomUsernameRequest.body)

        //form encoded urls
        assertFalse(getRandomUsernameRequest.formEncodeUrls)
    }
}
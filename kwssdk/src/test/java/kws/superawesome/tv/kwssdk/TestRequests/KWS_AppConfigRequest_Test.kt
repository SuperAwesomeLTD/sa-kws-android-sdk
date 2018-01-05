package kws.superawesome.tv.kwssdk.TestRequests


import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Created by guilherme.mota on 29/12/2017.
 */
class KWS_AppConfigRequest_Test {

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
        val clientID = "stan-test"
        val endpoint = "v1/apps/config"
        val method = NetworkMethod.GET

        //when
        val getAppConfigRequest = AppConfigRequest(
                environment = kwsNetworkEnvironmnet,
                clientID = kwsNetworkEnvironmnet.appID)


        //then
        assertNotNull(getAppConfigRequest)

        // clientID
        assertNotNull(clientID)

        //headers
        assertThat<Map<String, String>>(getAppConfigRequest.headers,
                IsMapContaining.hasEntry("Content-Type", "application/json"))

        //endpoint
        assertEquals(getAppConfigRequest.endpoint, endpoint)

        //method type
        assertEquals(getAppConfigRequest.method, method)

        //query parameters
        assertThat<Map<String, String>>(getAppConfigRequest.query as Map<String, String>?,
                IsMapContaining.hasEntry("oauthClientId", clientID))

        //body
        assertNull(getAppConfigRequest.body)

        //form encoded urls
        assertFalse(getAppConfigRequest.formEncodeUrls)

    }

}
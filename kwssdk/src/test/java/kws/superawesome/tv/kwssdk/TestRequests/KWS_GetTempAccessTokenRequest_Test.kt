package kws.superawesome.tv.kwssdk.TestRequests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.GetTempAccessTokenRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsMapContaining
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by guilherme.mota on 15/12/2017.
 */
class KWS_GetTempAccessTokenRequest_Test {


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
    fun GetTempAccessTokenRequest_Valid_Headers() {

        //given


        //when
        val getTempAccessTokenRequest = GetTempAccessTokenRequest(
                environment = kwsNetworkEnvironmnet,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
        assertNotNull(getTempAccessTokenRequest)
        MatcherAssert.assertThat<Map<String, String>>(getTempAccessTokenRequest.headers as Map<String, String>?, IsMapContaining.hasEntry("Content-Type", "application/x-www-form-urlencoded"))


    }


    @Test
    fun GetTempAccessTokenRequest_Valid_Endpoint() {

        //given
        val endpoint = "oauth/token"


        //when
        val getTempAccessTokenRequest = GetTempAccessTokenRequest(
                environment = kwsNetworkEnvironmnet,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
        assertNotNull(getTempAccessTokenRequest)
        assertEquals(getTempAccessTokenRequest.endpoint, endpoint)


    }


    @Test
    fun GetTempAccessTokenRequest_NetworkMethod_POST() {

        //given
        val method = NetworkMethod.POST

        //when
        val getTempAccessTokenRequest = GetTempAccessTokenRequest(
                environment = kwsNetworkEnvironmnet,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)

        //then
        assertNotNull(getTempAccessTokenRequest)
        assertEquals(getTempAccessTokenRequest.method, method)


    }


    @Test
    fun GetTempAccessTokenRequest_Valid_Body() {

        //given


        //when
        val getTempAccessTokenRequest = GetTempAccessTokenRequest(
                environment = kwsNetworkEnvironmnet,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
        assertNotNull(getTempAccessTokenRequest)
        MatcherAssert.assertThat(getTempAccessTokenRequest.body!!.size, CoreMatchers.`is`(3))
        MatcherAssert.assertThat<Map<String, String>>(getTempAccessTokenRequest.body as Map<String, String>?, IsMapContaining.hasEntry("grant_type", "client_credentials"))
        MatcherAssert.assertThat(getTempAccessTokenRequest.body, IsMapContaining.hasKey("client_id"))
        MatcherAssert.assertThat(getTempAccessTokenRequest.body, IsMapContaining.hasKey("client_secret"))

    }

}
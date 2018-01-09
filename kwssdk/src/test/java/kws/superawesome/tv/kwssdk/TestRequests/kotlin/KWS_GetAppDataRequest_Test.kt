package kws.superawesome.tv.kwssdk.TestRequests.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.GetAppDataRequest
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 08/01/2018.
 */
@Deprecated("Not used anymore")
class KWS_GetAppDataRequest_Test {


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
    fun SetAppDataRequest_Valid() {

        //given
        val appId = 2
        val userId = 25
        val endpoint = "v1/apps/$appId/users/$userId/app-data"
        val method = NetworkMethod.GET
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc" +
                "3QiLCJzY29wZSI6Im1vYmlsZUFwcCIsImlhdCI6MTUxMzM1MDM0NywiZXhwIjoxNTEzNDM2NzQ3LCJpc3Mi" +
                "OiJzdXBlcmF3ZXNvbWUifQ.zBLzyW3RJC7ybZyOeKq5SLulq6L1SmIpdDSByR1rLAc"


        //when
        val getAppDataRequest = GetAppDataRequest(
                environment = kwsNetworkEnvironmnet,
                appId = appId,
                userId = userId,
                token = token

        )


//        //then
//        assertNotNull(getAppDataRequest)
//
//        // token
//        assertNotEquals(token, "")
//        assertNotNull(token)
//
//
//        // appid
//        assertNotEquals(appId, 0)
//
//        //headers
//        MatcherAssert.assertThat<Map<String, String>>(getAppDataRequest.headers,
//                IsMapContaining.hasEntry("Content-Type", "application/json"))
//
//        //endpoint
//        assertEquals(getAppDataRequest.endpoint, endpoint)
//
//        //query parameters
//        assertNull(getAppDataRequest.query)
//
//        //body
//        assertNull(getAppDataRequest.body)
//
//        //method request type
//        assertEquals(getAppDataRequest.method, method)
//
//        //form encoded urls
//        assertFalse(getAppDataRequest.formEncodeUrls)


    }


}
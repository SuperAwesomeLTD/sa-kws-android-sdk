package kws.superawesome.tv.kwssdk.TestRequests.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Created by guilherme.mota on 15/12/2017.
 */
class KWS_LoginUserRequest_Test {

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
    fun LoginUserRequest_Valid() {

        //given
        val username = "testuser9112"
        val password = "testtest"

        val endpoint = "oauth/token"
        val method = NetworkMethod.POST

        //when
        val loginUserRequest = LoginUserRequest(
                environment = kwsNetworkEnvironmnet,
                username = username,
                password = password,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
        assertNotNull(loginUserRequest)

        // username
        assertNotNull(username)

        //password
        assertNotNull(password)

        //headers
        assertThat<Map<String, String>>(loginUserRequest.headers as Map<String, String>?,
                IsMapContaining.hasEntry("Content-Type", "application/x-www-form-urlencoded"))

        //endpoint
        assertEquals(loginUserRequest.endpoint, endpoint)

        //method type
        assertEquals(loginUserRequest.method, method)

        //query
        assertNull(loginUserRequest.query)

        //body
        assertThat(loginUserRequest.body!!.size, `is`(5))
        assertThat<Map<String, String>>(loginUserRequest.body as Map<String, String>?, IsMapContaining.hasEntry("grant_type", "password"))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("username"))
        assertThat<Map<String, String>>(loginUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("username", "")))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("password"))
        assertThat<Map<String, String>>(loginUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("password", "")))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("client_id"))
        assertThat<Map<String, String>>(loginUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("client_id", "")))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("client_secret"))
        assertThat<Map<String, String>>(loginUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("client_secret", "")))

        //form url encoded
        assertTrue(loginUserRequest.formEncodeUrls)

    }


}
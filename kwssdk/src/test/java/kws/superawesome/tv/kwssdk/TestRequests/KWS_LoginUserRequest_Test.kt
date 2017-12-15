package kws.superawesome.tv.kwssdk.TestRequests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import org.junit.Assert

import org.junit.Test
import tv.superawesome.samobilebase.network.NetworkMethod
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
    fun LoginUserRequest_Valid_Headers(){

        //given
        val username = "testuser9112"
        val password = "testtest"


        //when
        val loginUserRequest = LoginUserRequest(
                environment = kwsNetworkEnvironmnet,
                username = username,
                password = password,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
        assertNotNull(loginUserRequest)
        assertThat<Map<String, String>>(loginUserRequest.headers as Map<String, String>?, IsMapContaining.hasEntry("Content-Type", "application/x-www-form-urlencoded"))


    }

    @Test
    fun LoginUserRequest_Valid_Endpoint() {

        //given
        val username = "testuser9112"
        val password = "testtest"
        val endpoint = "oauth/token"


        //when
        val loginUserRequest = LoginUserRequest(
                environment = kwsNetworkEnvironmnet,
                username = username,
                password = password,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
        assertNotNull(loginUserRequest)
        assertEquals(loginUserRequest.endpoint, endpoint)


    }


    @Test
    fun LoginUserRequest_NetworkMethod_POST() {

        //given
        val username = "testuser9112"
        val password = "testtest"

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
        assertEquals(loginUserRequest.method, method)


    }


    @Test
    fun LoginUserRequest_Valid_Body() {

        //given
        val username = "testuser9112"
        val password = "testtest"


        //when
        val loginUserRequest = LoginUserRequest(
                environment = kwsNetworkEnvironmnet,
                username = username,
                password = password,
                clientID = kwsNetworkEnvironmnet.appID,
                clientSecret = kwsNetworkEnvironmnet.mobileKey)


        //then
        assertNotNull(loginUserRequest)
        assertThat(loginUserRequest.body!!.size, `is`(5))
        assertThat<Map<String, String>>(loginUserRequest.body as Map<String, String>?, IsMapContaining.hasEntry("grant_type", "password"))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("username"))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("password"))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("client_id"))
        assertThat(loginUserRequest.body, IsMapContaining.hasKey("client_secret"))

    }

}
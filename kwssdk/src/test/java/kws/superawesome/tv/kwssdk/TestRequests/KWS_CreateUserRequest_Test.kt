package kws.superawesome.tv.kwssdk.TestRequests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.CreateUserRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import tv.superawesome.lib.sautils.SAUtils
import tv.superawesome.samobilebase.network.NetworkMethod

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import kotlin.test.*


/**
 * Created by guilherme.mota on 15/12/2017.
 */
class KWS_CreateUserRequest_Test {


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
    fun CreateUserRequest_Valid() {

        //given
        val username = "testuser" + SAUtils.randomNumberBetween(100, 10000)
        val password = "testtest"
        val dateOfBirth = "2011-03-02"
        val country = "US"
        val parentEmail = "dev.gabriel.coman@gmail.com"
        val appID = 2
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc" +
                "3QiLCJzY29wZSI6Im1vYmlsZUFwcCIsImlhdCI6MTUxMzM1MDM0NywiZXhwIjoxNTEzNDM2NzQ3LCJpc3Mi" +
                "OiJzdXBlcmF3ZXNvbWUifQ.zBLzyW3RJC7ybZyOeKq5SLulq6L1SmIpdDSByR1rLAc"

        val endpoint = "v1/apps/$appID/users"
        val method = NetworkMethod.POST

        //when
        val createUserRequest = CreateUserRequest(
                environment = kwsNetworkEnvironmnet,
                username = username,
                password = password,
                dateOfBirth = dateOfBirth,
                country = country,
                parentEmail = parentEmail,
                appID = appID,
                token = token
                )


        //then
        assertNotNull(createUserRequest)

        // token
        assertNotEquals(token,"")
        assertNotNull(token)

        //password
        assertNotNull(password)

        // appid
        assertNotEquals(appID,0)

        //headers
        MatcherAssert.assertThat<Map<String, String>>(createUserRequest.headers as Map<String, String>?,
                IsMapContaining.hasEntry("Content-Type", "application/json"))

        //endpoint
        assertEquals(createUserRequest.endpoint, endpoint)

        //query parameters
        assertThat<Map<String, String>>(createUserRequest.query as Map<String, String>?,
                IsMapContaining.hasEntry("access_token", token))

        //body
        assertThat(createUserRequest.body!!.size, `is`(6))
        assertThat<Map<String, Boolean>>(createUserRequest.body as Map<String, Boolean>?, IsMapContaining.hasEntry("authenticate", true))
        assertThat(createUserRequest.body, IsMapContaining.hasKey("username"))
        assertThat<Map<String, String>>(createUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("username", "")))
        assertThat(createUserRequest.body, IsMapContaining.hasKey("password"))
        assertThat<Map<String, String>>(createUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("password", "")))
        assertThat(createUserRequest.body, IsMapContaining.hasKey("dateOfBirth"))
        assertThat<Map<String, String>>(createUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("dateOfBirth", "")))
        assertThat(createUserRequest.body, IsMapContaining.hasKey("country"))
        assertThat<Map<String, String>>(createUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("country", "")))
        assertThat(createUserRequest.body, IsMapContaining.hasKey("parentEmail"))
        assertThat<Map<String, String>>(createUserRequest.body as Map<String, String>?, not(IsMapContaining.hasEntry("parentEmail", "")))

        //method request type
        assertEquals(createUserRequest.method, method)

        //form encoded urls
        assertFalse(createUserRequest.formEncodeUrls)


    }

}
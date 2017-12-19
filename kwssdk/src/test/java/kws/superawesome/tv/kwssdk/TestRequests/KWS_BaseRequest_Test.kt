package kws.superawesome.tv.kwssdk.TestRequests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.BaseRequest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsMapContaining
import org.junit.Test

/**
 * Created by guilherme.mota on 15/12/2017.
 */
class KWS_BaseRequest_Test {

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

    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyLCJhcHBJZCI6MSwiY2xpZW50S" +
            "WQiOiJzdXBlcmF3ZXNvbWVjbHViIiwic2NvcGUiOiJzc28sdXNlciIsImlhdCI6MTUxMzI0OTQ1MSwiZXhwIjox" +
            "NTEzMzM1ODUxLCJpc3MiOiJzdXBlcmF3ZXNvbWUifQ.nvY6HhRvxMv5fgVRTm1CJeRMTtso5Ex_nuDns4pMRas"


    internal val baseRequest = object : BaseRequest(environment = kwsNetworkEnvironmnet, token = token) {
        override val endpoint: String
            get() = ""

    }

    @Test
    fun BaseRequest_Default_Header_Valid() {

        //given all the global vars above


        //when
        val defaultHeader = baseRequest.defaultHeader


        //then
        assertThat(defaultHeader.size, `is`(3))
        assertThat(defaultHeader, IsMapContaining.hasKey("Accept-Language"))
        assertThat(defaultHeader, IsMapContaining.hasKey("X-Client-Version"))
        assertThat(defaultHeader, IsMapContaining.hasKey("Content-Type"))


    }


    @Test
    fun BaseRequest_Auth_Header_Valid() {


        //given the token


        //when
        val authHeader = baseRequest.authHeader

        //then
        assertThat(authHeader!!.size, `is`(1))
        assertThat(authHeader, IsMapContaining.hasKey("Authorization"))
        assertThat(authHeader, IsMapContaining.hasValue("Bearer $token"))


    }

}
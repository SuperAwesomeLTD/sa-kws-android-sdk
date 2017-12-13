package kws.superawesome.tv.kwsdemo.kotlin

import kws.superawesome.tv.kwssdk.base.KWSSDK
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.services.LoginService
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations


/**
 * Created by guilherme.mota on 13/12/2017.
 */
class KWSChildrenTest {

    var kwsEnvironment: KWSNetworkEnvironment? = null

    private val APPID = "stan-test" // "superawesomeclub";
    private val KEY = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4"
    private val URL = "https://stan-test-cluster.api.kws.superawesome.tv/"

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        kwsEnvironment = object : KWSNetworkEnvironment {
            override val appID: String
                get() = APPID

            override val mobileKey: String
                get() = KEY

            override val domain: String
                get() = URL
        }

    }


    @Test
    fun doLogin_Success() {


        //given
        val username = "testuser9112"
        val password = "testtest"

        val loginService = KWSSDK.get(kwsEnvironment!!, LoginService::class.java)


        loginService?.loginUser(username, password) { loggedUser, throwable ->
            if (loggedUser != null && throwable == null) {


            } else {

            }
        }
    }

}
package kws.superawesome.tv.kwsdemo.kotlin

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.services.LoginService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import tv.superawesome.samobilebase.network.NetworkUrlEncodedTask
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

/**
 * Created by guilherme.mota on 13/12/2017.
 */
class LoginProviderTest {

    @Mock
    lateinit var mockLogingService: LoginService

    @Mock
    lateinit var kwsEnvironment: KWSNetworkEnvironment

    @Mock
    lateinit var urlEncodedTask: NetworkUrlEncodedTask

//    @Mock
//    lateinit var request: LoginUserRequest

//    @Mock
//    lateinit var loggedUser: LoggedUser

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun testNotNullLoginService() {
        assertNotNull(mockLogingService)
    }

//    @Test
//    fun testNotNullLoggedUserModel() {
//        assertNotNull(loggedUser)
//    }

    @Test
    fun testNotNullKWSEnvironment() {
        assertNotNull(kwsEnvironment)
    }


    @Test
    fun validUserName() {
        val correctUserName = "testuser9112"
        assertNotNull(correctUserName)
        assertNotEquals("", correctUserName)
    }

    @Test
    fun invalidUserName() {
        val incorrectUserName = ""
        assertNotNull(incorrectUserName)
        assertEquals("", incorrectUserName)
    }

    @Test
    fun validPassword() {
        val correctPassword = "testtest"
        assertNotNull(correctPassword)
        assertNotEquals("", correctPassword)
    }

    @Test
    fun invalidPassword() {
        val incorrectPassword = ""
        assertNotNull(incorrectPassword)
        assertEquals("", incorrectPassword)
    }

    @Test
    fun loginWithCorrectCredentials() {

        //given

        val mock = Mockito.mock(LoginService::class.java)


        val correctUserName = "testuser9112"
        val correctPassword = "testtest"

        mock.loginUser(correctUserName, correctPassword) { user, error ->
            System.out.print(user)
            if (user != null && error == null) {

            } else {

            }

        }


        //when


        //then


    }

}
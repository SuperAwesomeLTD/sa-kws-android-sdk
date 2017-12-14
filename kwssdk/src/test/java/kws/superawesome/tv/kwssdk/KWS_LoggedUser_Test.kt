package kws.superawesome.tv.kwssdk.kotlin


import android.util.Base64
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.io.UnsupportedEncodingException


/**
 * Created by guilherme.mota on 14/12/2017.
 */

//@RunWith(PowerMockRunner::class)
//@PrepareForTest(Base64::class)
class KWS_LoggedUser_Test {


    @Mock
    val base64Mock: Base64? = null

    @Before
    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        PowerMockito.mockStatic(Base64::class.java)

    }


//    var mockLoanCalculator: Base64




    @Test
    fun Create_Logged_User_Success() {

        //given

        var mockLoanCalculator = mock(Base64::class.java)

//        mockLoanCalculator.de


        //this token is for user "testuser8865"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjI1LCJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc3QiLCJzY29wZSI6InVzZXIiLCJpYXQiOjE1MTMyNjI1MzQsImV4cCI6MTUxMzM0ODkzNCwiaXNzIjoic3VwZXJhd2Vzb21lIn0._aRFbkTK-cihOvUHn31ET_0sXznTYVlrvYUD07hJW4o"

        val decodeResult = "{\n" +
                "  \"userId\": 42,\n" +
                "  \"appId\": 1,\n" +
                "  \"clientId\": \"superawesomeclub\",\n" +
                "  \"scope\": \"sso,user\",\n" +
                "  \"iat\": 1513264341,\n" +
                "  \"exp\": 1513350741,\n" +
                "  \"iss\": \"superawesome\"\n" +
                "}"

        val toByteArray = decodeResult.toByteArray(Charsets.UTF_8)

        `when`(Base64.decode(token, Base64.DEFAULT)).thenReturn(toByteArray)


        val clientId = "superawesomeclub"
        val appId = 1
        val userId = 42


//        val processMetadata = KWSMetadata.processMetadata(token)

        //when
//        val loggedUser = LoggedUser(token, processMetadata!!)


        //then
//        assertEquals(loggedUser.kwsMetaData.clientId, clientId)
//        assertEquals(loggedUser.kwsMetaData.appId, appId)
//        assertEquals(loggedUser.kwsMetaData.userId, userId)


    }



}
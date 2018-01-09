package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.responses.Login
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by guilherme.mota on 14/12/2017.
 */
class KWS_LoginResponse_Test {

    @Test
    fun Check_Response_Valid_Token() {

        //given
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyLCJhcHBJZCI6MSwiY2xpZW50S" +
                "WQiOiJzdXBlcmF3ZXNvbWVjbHViIiwic2NvcGUiOiJzc28sdXNlciIsImlhdCI6MTUxMzI0OTQ1MSwiZXhwIjox" +
                "NTEzMzM1ODUxLCJpc3MiOiJzdXBlcmF3ZXNvbWUifQ.nvY6HhRvxMv5fgVRTm1CJeRMTtso5Ex_nuDns4pMRas"

        //when
        val loginResponse = Login(token = token)

        //then
//        assertNotNull(loginResponse)
        assertEquals(loginResponse.token, token)

    }


    @Test
    fun Check_Response_Not_Valid_Token_Null() {


        //given
        val token = null

        //when
        val loginResponse = Login(token)

        //then
//        assertNotNull(loginResponse)
//        assertNull(loginResponse.token)


    }


}
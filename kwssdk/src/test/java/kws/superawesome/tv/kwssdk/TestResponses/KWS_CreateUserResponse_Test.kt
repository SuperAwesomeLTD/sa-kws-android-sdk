package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.models.CreateUser
import org.junit.Test

/**
 * Created by guilherme.mota on 15/12/2017.
 */
class KWS_CreateUserResponse_Test {


    @Test
    fun Check_Response_Valid_Token_And_Id() {

        //given
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQyLCJhcHBJZCI6MSwiY2xpZW50S" +
                "WQiOiJzdXBlcmF3ZXNvbWVjbHViIiwic2NvcGUiOiJzc28sdXNlciIsImlhdCI6MTUxMzI0OTQ1MSwiZXhwIjox" +
                "NTEzMzM1ODUxLCJpc3MiOiJzdXBlcmF3ZXNvbWUifQ.nvY6HhRvxMv5fgVRTm1CJeRMTtso5Ex_nuDns4pMRas"

        val id = 47


        //when
        val createUserResponse = CreateUser(token = token, id = id)

        //then
//        assertNotNull(createUserResponse)
//        assertEquals(createUserResponse.token, token)
//        assertEquals(createUserResponse.id, id)


    }

    @Test
    fun Check_Response_Not_Valid_Token_And_Id(){


        //given
        val token = ""
        val id = 0

        //when
        val createUserResponse = CreateUser(token = token, id = id)


        //then
//        assertNotNull(createUserResponse)
//        assertNull(createUserResponse.token, token)
//        assertEquals(createUserResponse.id, id)


    }


}
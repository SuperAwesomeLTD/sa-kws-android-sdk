package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.responses.GetRandomUsernameResponse
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Created by guilherme.mota on 29/12/2017.
 */
class KWS_GetRandomUsernameResponse_Test {

    @Test
    fun Check_Response_Valid_Random_Username() {

        //given
        val randomUsername = "coolgabriel7443"


        //when
        val getRandomUsername = GetRandomUsernameResponse(randomUsername = randomUsername)

        //then
        assertNotNull(getRandomUsername)
        assertEquals(getRandomUsername.randomUsername, randomUsername)


    }

    @Test
    fun Check_Response_Not_Valid_Random_Username(){


        //given
        val randomUsername = null

        //when
        val getRandomUsername = GetRandomUsernameResponse(randomUsername = randomUsername)


        //then
        assertNotNull(getRandomUsername)
        assertNull(getRandomUsername.randomUsername, randomUsername)


    }


}
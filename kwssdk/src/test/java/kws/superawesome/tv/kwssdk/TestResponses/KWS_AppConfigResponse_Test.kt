package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.responses.AppConfigAppObjectResponse
import kws.superawesome.tv.kwssdk.base.responses.AppConfigResponse
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Created by guilherme.mota on 29/12/2017.
 */
class KWS_AppConfigResponse_Test {

    @Test
    fun Check_Response_Valid_Id_Name() {

        //given
        val id = 2
        val name = "stan-test"


        //when
        val appConfigAppObjectResponse = AppConfigAppObjectResponse(id = id, name = name)
        val getRandomUsername = AppConfigResponse(appConfigAppObject = appConfigAppObjectResponse)

        //then
        assertNotNull(appConfigAppObjectResponse)
        assertNotNull(getRandomUsername)
        assertEquals(appConfigAppObjectResponse.id, id)
        assertEquals(appConfigAppObjectResponse.name, name)


    }

    @Test
    fun Check_Response_Not_Valid_Id_Name() {


        //given
        val id = 0
        val name = null

        //when
        val appConfigAppObjectResponse = AppConfigAppObjectResponse(id = id, name = name)
        val getRandomUsername = AppConfigResponse(appConfigAppObject = appConfigAppObjectResponse)


        //then
        assertNotNull(appConfigAppObjectResponse)
        assertNotNull(getRandomUsername)
        assertNull(appConfigAppObjectResponse.name)
        assertEquals(appConfigAppObjectResponse.id, id)


    }

}
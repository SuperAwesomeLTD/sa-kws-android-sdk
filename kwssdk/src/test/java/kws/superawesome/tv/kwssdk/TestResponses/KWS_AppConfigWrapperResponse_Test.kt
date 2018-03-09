package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.models.AppConfigWrapper
import kws.superawesome.tv.kwssdk.base.models.AppConfig
import org.junit.Test

/**
 * Created by guilherme.mota on 29/12/2017.
 */
class KWS_AppConfigWrapperResponse_Test {

    @Test
    fun Check_Response_Valid_Id_Name() {

        //given
        val id = 2
        val name = ""


        //when
        val appConfigAppObjectResponse = AppConfig(id = id, name = name)
        val getRandomUsername = AppConfigWrapper(app = appConfigAppObjectResponse)

        //then
//        assertNotNull(appConfigAppObjectResponse)
//        assertNotNull(getRandomUsername)
//        assertEquals(appConfigAppObjectResponse.userId, userId)
//        assertEquals(appConfigAppObjectResponse.name, name)


    }

    @Test
    fun Check_Response_Not_Valid_Id_Name() {


        //given
        val id = 0
        val name = null

        //when
        val appConfigAppObjectResponse = AppConfig(id = id, name = name)
        val getRandomUsername = AppConfigWrapper(app = appConfigAppObjectResponse)


        //then
//        assertNotNull(appConfigAppObjectResponse)
//        assertNotNull(getRandomUsername)
//        assertNull(appConfigAppObjectResponse.name)
//        assertEquals(appConfigAppObjectResponse.userId, userId)


    }

}
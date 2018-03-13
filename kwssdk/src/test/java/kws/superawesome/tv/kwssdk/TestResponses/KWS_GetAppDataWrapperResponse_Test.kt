package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.models.AppDataWrapper
import kws.superawesome.tv.kwssdk.base.models.AppData
import org.junit.Test

/**
 * Created by guilherme.mota on 08/01/2018.
 */
class KWS_GetAppDataWrapperResponse_Test {

    @Test
    fun Check_Response_Valid_AppData() {

        //given
        val listOfAppDataDetails = ArrayList<AppData>()
        listOfAppDataDetails.add(AppData(name = "new_val1", value = 1))
        listOfAppDataDetails.add(AppData(name = "new_val2", value = 2))
        listOfAppDataDetails.add(AppData(name = "new_val3", value = 3))

        val offSet = 0
        val limit = 1000

        //when
        val getLeaders = AppDataWrapper(
                results = listOfAppDataDetails,
                count = listOfAppDataDetails.size,
                offset = offSet,
                limit = limit
        )

        //then
//        assertNotNull(getLeaders)
//        assertNotNull(getLeaders.results)
//        assertNotNull(getLeaders.count)
//        assertNotNull(getLeaders.offset)
//        assertNotNull(getLeaders.limit)

    }

    @Test
    fun Check_Response_Not_Valid_AppData() {

        //given
        val listOfLeaderDetails: ArrayList<AppData> = ArrayList()
        val offSet = 0
        val limit = 0

        //when
        val getLeaders = AppDataWrapper(
                results = listOfLeaderDetails,
                count = listOfLeaderDetails.size,
                offset = offSet,
                limit = limit
        )

        //then
//        assertNotNull(getLeaders)
//        assertEquals(getLeaders.results, ArrayList())
//        assertEquals(getLeaders.count, 0)
//        assertEquals(getLeaders.offset, 0)
//        assertEquals(getLeaders.limit, 0)


    }


}
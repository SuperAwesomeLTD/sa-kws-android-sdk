package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.models.LeadersWrapper
import kws.superawesome.tv.kwssdk.base.models.Leaders
import org.junit.Test

/**
 * Created by guilherme.mota on 05/01/2018.
 */
class KWS_LeadersWrapperResponse_Test {


    @Test
    fun Check_Response_Valid_Leaders() {

        //given
        val listOfLeaderDetails = ArrayList<Leaders>()
        listOfLeaderDetails.add(Leaders(name = "tersturs1234", score = 11, rank = 1))
        listOfLeaderDetails.add(Leaders(name = "tersturs4567", score = 12, rank = 2))
        listOfLeaderDetails.add(Leaders(name = "tersturs7891", score = 13, rank = 3))
        listOfLeaderDetails.add(Leaders(name = "tersturs2345", score = 14, rank = 4))

        val offSet = 0
        val limit = 1000

        //when
        val getLeaders = LeadersWrapper(
                results = listOfLeaderDetails,
                count = listOfLeaderDetails.size,
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
    fun Check_Response_Not_Valid_Leaders() {

        //given
        val listOfLeaders: ArrayList<Leaders> = ArrayList()
        val offSet = 0
        val limit = 0

        //when
        val getLeaders = LeadersWrapper(
                results = listOfLeaders,
                count = listOfLeaders.size,
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
package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.responses.Leaders
import kws.superawesome.tv.kwssdk.base.responses.LeadersDetail
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by guilherme.mota on 05/01/2018.
 */
class KWS_LeadersResponse_Test {


    @Test
    fun Check_Response_Valid_Leaders() {

        //given
        val listOfLeaderDetails = ArrayList<LeadersDetail>()
        listOfLeaderDetails.add(LeadersDetail(user = "tersturs1234", score = 11, rank = 1))
        listOfLeaderDetails.add(LeadersDetail(user = "tersturs4567", score = 12, rank = 2))
        listOfLeaderDetails.add(LeadersDetail(user = "tersturs7891", score = 13, rank = 3))
        listOfLeaderDetails.add(LeadersDetail(user = "tersturs2345", score = 14, rank = 4))

        val offSet = 0
        val limit = 1000

        //when
        val getLeaders = Leaders(
                results = listOfLeaderDetails,
                count = listOfLeaderDetails.size,
                offset = offSet,
                limit = limit
        )

        //then
        assertNotNull(getLeaders)
        assertNotNull(getLeaders.results)
        assertNotNull(getLeaders.count)
        assertNotNull(getLeaders.offset)
        assertNotNull(getLeaders.limit)

    }


    @Test
    fun Check_Response_Not_Valid_Leaders() {

        //given
        val listOfLeaderDetails: ArrayList<LeadersDetail> = ArrayList()
        val offSet = 0
        val limit = 0

        //when
        val getLeaders = Leaders(
                results = listOfLeaderDetails,
                count = listOfLeaderDetails.size,
                offset = offSet,
                limit = limit
        )

        //then
        assertNotNull(getLeaders)
        assertEquals(getLeaders.results, ArrayList())
        assertEquals(getLeaders.count, 0)
        assertEquals(getLeaders.offset, 0)
        assertEquals(getLeaders.limit, 0)


    }


}
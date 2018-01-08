package kws.superawesome.tv.kwssdk.TestResponses

import kws.superawesome.tv.kwssdk.base.responses.Score
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by guilherme.mota on 08/01/2018.
 */
class KWS_ScoreResponse_Test {


    @Test
    fun Check_Response_Valid_Score() {

        //given
        val score = 480
        val rank = 1

        //when
        val getScore = Score(
                score = score,
                rank = rank
        )

        //then
        assertNotNull(getScore)
        assertNotNull(getScore.score)
        assertNotNull(getScore.rank)

    }


    @Test
    fun Check_Response_Not_Valid_Score() {

        //given
        //given
        val score = 0
        val rank = 0

        //when
        //when
        val getScore = Score(
                score = score,
                rank = rank
        )

        //then
        assertNotNull(getScore)
        assertEquals(getScore.rank, 0)
        assertEquals(getScore.rank, 0)


    }
}
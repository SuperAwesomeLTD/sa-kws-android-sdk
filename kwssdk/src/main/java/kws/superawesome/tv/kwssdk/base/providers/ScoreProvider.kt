package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LeadersWrapper
import kws.superawesome.tv.kwssdk.base.models.Score
import kws.superawesome.tv.kwssdk.base.requests.LeadersRequest
import kws.superawesome.tv.kwssdk.base.requests.UserScoreRequest
import tv.superawesome.protobufs.features.scoring.IScoringService
import tv.superawesome.protobufs.models.score.ILeaderWrapperModel
import tv.superawesome.protobufs.models.score.IScoreModel
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 13/03/2018.
 */


@PublishedApi
internal class ScoreProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : IScoringService {


    override fun getLeaderboard(appId: Int, token: String, callback: (leaderboard: ILeaderWrapperModel?, error: Throwable?) -> Unit) {

        val getLeadersNetworkRequest = LeadersRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        networkTask.execute(input = getLeadersNetworkRequest) { getLeadersNetworkResponse ->

            //
            // network success case
            if (getLeadersNetworkResponse.response != null && getLeadersNetworkResponse.error == null) {
                val parseRequest = ParseJsonRequest(rawString = getLeadersNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getLeadersResponseObject = parseTask.execute<LeadersWrapper>(input = parseRequest,
                        clazz = LeadersWrapper::class.java)

                //
                // send callback
                val error = if (getLeadersResponseObject != null) null else Throwable("Error getting leader details")
                callback(getLeadersResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, getLeadersNetworkResponse.error)
            }

        }


    }

    override fun getScore(appId: Int, token: String, callback: (score: IScoreModel?, error: Throwable?) -> Unit) {

        val getUserScoreNetworkRequest = UserScoreRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        networkTask.execute(input = getUserScoreNetworkRequest) { getUserScoreNetworkResponse ->


            if (getUserScoreNetworkResponse.response != null && getUserScoreNetworkResponse.error == null) {
                val parseRequest = ParseJsonRequest(rawString = getUserScoreNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getScoreResponseObject = parseTask.execute<Score>(input = parseRequest,
                        clazz = Score::class.java)

                //
                //send callback
                val error = if (getScoreResponseObject == null) Throwable("Error getting user score") else null;
                callback(getScoreResponseObject, error)

            } else {
                //
                // network failure

                callback(null, getUserScoreNetworkResponse.error)
            }
        }
    }


}


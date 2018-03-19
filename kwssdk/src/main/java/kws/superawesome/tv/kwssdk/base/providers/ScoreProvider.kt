package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LeadersWrapper
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.models.Score
import kws.superawesome.tv.kwssdk.base.requests.LeadersRequest
import kws.superawesome.tv.kwssdk.base.requests.UserScoreRequest
import org.json.JSONException
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
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask), IScoringService {


    override fun getLeaderboard(appId: Int, token: String, callback: (leaderboard: ILeaderWrapperModel?, error: Throwable?) -> Unit) {

        val getLeadersNetworkRequest = LeadersRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        networkTask.execute(input = getLeadersNetworkRequest) { payload ->

            //
            // network success case
            if (payload.success && payload.response != null) {

                val parseTask = ParseJsonTask()
                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val result = parseTask.execute<LeadersWrapper>(input = parseRequest,
                        clazz = LeadersWrapper::class.java)

                //parse error
                if (result == null) {

                    val error = JSONException(LeadersWrapper::class.java.toString())
                    callback(null, error)

                } else {

                    //send callback
                    callback(result, null)

                }

            }
            //
            // network failure
            else if (payload.error != null) {
                val error = super.parseServerError(serverError = payload.error)
                callback(null, error)
            }
            //
            // unknown error
            else {
                val error = SDKException()
                callback(null, error)
            }

        }


    }

    override fun getScore(appId: Int, token: String, callback: (score: IScoreModel?, error: Throwable?) -> Unit) {

        val getUserScoreNetworkRequest = UserScoreRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        networkTask.execute(input = getUserScoreNetworkRequest) { payload ->


            if (payload.success && payload.response != null) {

                val parseTask = ParseJsonTask()
                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val result = parseTask.execute<Score>(input = parseRequest,
                        clazz = Score::class.java)

                //parse error
                if (result == null) {

                    val error = JSONException(Score::class.java.toString())
                    callback(null, error)

                } else {

                    //send callback
                    callback(result, null)

                }

            }
            //
            // network failure
            else if (payload.error != null) {
                val error = super.parseServerError(serverError = payload.error)
                callback(null, error)
            }
            //
            // unknown error
            else {
                val error = Throwable("Unknown error")
                callback(null, error)
            }
        }
    }


}


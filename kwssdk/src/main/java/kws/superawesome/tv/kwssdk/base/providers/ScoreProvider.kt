package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LeadersWrapper
import kws.superawesome.tv.kwssdk.base.models.Score
import kws.superawesome.tv.kwssdk.base.requests.LeadersRequest
import kws.superawesome.tv.kwssdk.base.requests.UserScoreRequest
import tv.superawesome.protobufs.features.scoring.IScoringService
import tv.superawesome.protobufs.models.score.ILeaderWrapperModel
import tv.superawesome.protobufs.models.score.IScoreModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
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

        val future = networkTask.execute(input = getLeadersNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = LeadersWrapper::class.java)
            val result = networkResult.then(parse::execute)

            when (result) {

                is Result.success -> {
                    callback(result.value, null)
                }

                is Result.error -> {
                    val serverError = parseServerError(error = result.error)
                    callback(null, serverError)
                }

            }
        }
    }

    override fun getScore(appId: Int, token: String, callback: (score: IScoreModel?, error: Throwable?) -> Unit) {

        val getUserScoreNetworkRequest = UserScoreRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        val future = networkTask.execute(input = getUserScoreNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = Score::class.java)
            val result = networkResult.then(parse::execute)

            when (result) {

                is Result.success -> {
                    callback(result.value, null)
                }

                is Result.error -> {
                    val serverError = parseServerError(error = result.error)
                    callback(null, serverError)
                }
            }
        }
    }


}


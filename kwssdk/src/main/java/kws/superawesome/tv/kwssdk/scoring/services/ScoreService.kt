package kws.superawesome.tv.kwssdk.base.scoring.services

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.common.services.AbstractService
import kws.superawesome.tv.kwssdk.base.scoring.models.LeadersWrapperModel
import kws.superawesome.tv.kwssdk.base.scoring.models.ScoreModel
import kws.superawesome.tv.kwssdk.base.scoring.requests.LeadersRequest
import kws.superawesome.tv.kwssdk.base.scoring.requests.ScoreRequest
import tv.superawesome.protobufs.score.models.ILeaderWrapperModel
import tv.superawesome.protobufs.score.models.IScoreModel
import tv.superawesome.protobufs.score.services.IScoringService
import tv.superawesome.samobilebase.common.result.Result
import tv.superawesome.samobilebase.json.ParseJsonTask
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 13/03/2018.
 */


@PublishedApi
internal class ScoreService
@JvmOverloads
constructor(override val environment: NetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : AbstractService(environment = environment, networkTask = networkTask), IScoringService {


    override fun getLeaderboard(appId: Int, token: String, callback: (leaderboard: ILeaderWrapperModel?, error: Throwable?) -> Unit) {

        val getLeadersNetworkRequest = LeadersRequest(
                environment = environment,
                appId = appId,
                token = token
        )


        val parseTask = ParseJsonTask(type = LeadersWrapperModel::class.java)
        val future = networkTask.execute(input = getLeadersNetworkRequest)
                .map { result -> result.then(parseTask::execute) }

        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> callback(networkResult.value, null)
                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }
        }
    }

    override fun getScore(appId: Int, token: String, callback: (score: IScoreModel?, error: Throwable?) -> Unit) {

        val getUserScoreNetworkRequest = ScoreRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        val parseTask = ParseJsonTask(type = ScoreModel::class.java)
        val future = networkTask.execute(input = getUserScoreNetworkRequest)
                .map { result -> result.then(parseTask::execute) }

        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> callback(networkResult.value, null)
                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }
        }
    }


}


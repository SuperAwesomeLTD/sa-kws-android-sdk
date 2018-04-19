package kws.superawesome.tv.kwssdk.base.scoring

import kws.superawesome.tv.kwssdk.base.BaseService
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.scoring.models.LeadersWrapper
import kws.superawesome.tv.kwssdk.base.scoring.models.Score
import kws.superawesome.tv.kwssdk.base.scoring.requests.LeadersRequest
import kws.superawesome.tv.kwssdk.base.scoring.requests.ScoreRequest
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
internal class ScoreService
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : BaseService(environment = environment, networkTask = networkTask), IScoringService {


    override fun getLeaderboard(appId: Int, token: String, callback: (leaderboard: ILeaderWrapperModel?, error: Throwable?) -> Unit) {

        val getLeadersNetworkRequest = LeadersRequest(
                environment = environment,
                appId = appId,
                token = token
        )


        val parseTask = ParseJsonTask(type = LeadersWrapper::class.java)
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

        val parseTask = ParseJsonTask(type = Score::class.java)
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


package kws.superawesome.tv.kwssdk.base.username.services

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.common.services.AbstractService
import kws.superawesome.tv.kwssdk.base.config.models.AppConfigWrapperModel
import kws.superawesome.tv.kwssdk.base.config.requests.AppConfigRequest
import kws.superawesome.tv.kwssdk.base.username.models.RandomUsernameModel
import kws.superawesome.tv.kwssdk.base.username.requests.RandomUsernameRequest
import tv.superawesome.protobufs.usernames.models.IRandomUsernameModel
import tv.superawesome.protobufs.usernames.models.IVerifiedUsernameModel
import tv.superawesome.protobufs.usernames.services.IUsernameService
import tv.superawesome.samobilebase.common.result.Result
import tv.superawesome.samobilebase.json.ParseJsonTask
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 29/12/2017.
 */
@PublishedApi
internal class UsernameService
@JvmOverloads
constructor(override val environment: NetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : AbstractService(environment = environment, networkTask = networkTask), IUsernameService {

    override fun getRandomUsername(callback: (username: IRandomUsernameModel?, error: Throwable?) -> Unit) {

        val appConfigNetworkRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.clientID
        )

        val parseTask = ParseJsonTask(AppConfigWrapperModel::class.java)
        val future = networkTask.execute(input = appConfigNetworkRequest).map { result -> result.then(parseTask::execute) }

        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> {

                    networkResult.value.app?.id?.let {
                        fetchRandomUsernameFromBackend(environment = environment, id = it, callback = callback)
                    }
                }

                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }

        }
    }

    private fun fetchRandomUsernameFromBackend(environment: NetworkEnvironment,
                                               id: Int,
                                               callback: (randomUser: RandomUsernameModel?, error: Throwable?) -> Unit) {

        val getRandomUsernameNetworkRequest = RandomUsernameRequest(
                environment = environment,
                appID = id)

        val future = networkTask.execute(input = getRandomUsernameNetworkRequest)

        future.onResult { networkResult ->

            when (networkResult) {
                is Result.success -> {

                    val responseString = networkResult.value

                    val randomUserName = responseString.replace("\"", "")

                    //
                    // send callback
                    if (randomUserName != null) {
                        callback(RandomUsernameModel(randomUserName), null)
                    } else {
                        callback(RandomUsernameModel(responseString), null)
                    }

                }
                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }
        }
    }

    override fun verifyUsername(username: String, callback: (validation: IVerifiedUsernameModel?, error: Throwable?) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
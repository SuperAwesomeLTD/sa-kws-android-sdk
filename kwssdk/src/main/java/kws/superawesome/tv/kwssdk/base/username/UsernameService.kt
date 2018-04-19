package kws.superawesome.tv.kwssdk.base.username

import kws.superawesome.tv.kwssdk.base.BaseService
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.app_config.models.AppConfigWrapper
import kws.superawesome.tv.kwssdk.base.app_config.requests.AppConfigRequest
import kws.superawesome.tv.kwssdk.base.username.models.RandomUsernameModel
import kws.superawesome.tv.kwssdk.base.username.requests.RandomUsernameRequest
import tv.superawesome.protobufs.features.auth.IUsernameService
import tv.superawesome.protobufs.models.usernames.IRandomUsernameModel
import tv.superawesome.protobufs.models.usernames.IVerifiedUsernameModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 29/12/2017.
 */
@PublishedApi
internal class UsernameService
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : BaseService(environment = environment, networkTask = networkTask), IUsernameService {

    override fun getRandomUsername(callback: (username: IRandomUsernameModel?, error: Throwable?) -> Unit) {

        val appConfigNetworkRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        val parseTask = ParseJsonTask(AppConfigWrapper::class.java)
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

    private fun fetchRandomUsernameFromBackend(environment: KWSNetworkEnvironment,
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
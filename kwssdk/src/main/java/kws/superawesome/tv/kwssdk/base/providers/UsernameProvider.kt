package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.RandomUsername
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.requests.RandomUsernameRequest
import org.json.JSONException
import tv.superawesome.protobufs.features.auth.IUsernameService
import tv.superawesome.protobufs.models.config.IAppConfigWrapperModel
import tv.superawesome.protobufs.models.config.IConfigModel
import tv.superawesome.protobufs.models.usernames.IRandomUsernameModel
import tv.superawesome.protobufs.models.usernames.IVerifiedUsernameModel
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 29/12/2017.
 */
@PublishedApi
internal class UsernameProvider
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask(),
            private val configProvider: ConfigProvider = ConfigProvider(environment, networkTask))
    : Provider(environment = environment, networkTask = networkTask), IUsernameService {

    override fun getRandomUsername(callback: (username: IRandomUsernameModel?, error: Throwable?) -> Unit) {

        configProvider.getConfig { appConfigWrapper: IConfigModel?, networkError: Throwable? ->

            if (networkError == null) {


                when (appConfigWrapper) {
                    is IAppConfigWrapperModel -> {

                        appConfigWrapper.app?.id?.let {

                            fetchRandomUsernameFromBackend(environment = environment, id = it, callback = callback)

                        } ?: run {

                            callback(null, SDKException())

                        }
                    }
                    else -> {
                        val error = JSONException(IAppConfigWrapperModel::class.java.toString())
                        callback(null, error)
                    }
                }

            } else {
                //
                // network failure
                callback(null, networkError)
            }

        }
    }

    private fun fetchRandomUsernameFromBackend(environment: KWSNetworkEnvironment,
                                               id: Int,
                                               callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit) {

        val getRandomUsernameNetworkRequest = RandomUsernameRequest(
                environment = environment,
                appID = id)

        networkTask.execute(input = getRandomUsernameNetworkRequest) { payload ->

            val responseString = payload.response

            if (payload.success && responseString != null) {

                val randomUserName = responseString.replace("\"", "")

                //
                // send callback
                if (randomUserName != null) {
                    callback(RandomUsername(randomUserName), null)
                } else {
                    callback(RandomUsername(responseString), null)
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

    override fun verifyUsername(username: String, callback: (validation: IVerifiedUsernameModel?, error: Throwable?) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
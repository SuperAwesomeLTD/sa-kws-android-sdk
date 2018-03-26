package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AppConfigWrapper
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest
import tv.superawesome.protobufs.features.config.IConfigService
import tv.superawesome.protobufs.models.config.IConfigModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 14/03/2018.
 */
@PublishedApi
internal class ConfigProvider
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask), IConfigService {

    override fun getConfig(callback: (config: IConfigModel?, error: Throwable?) -> Unit) {

        val appConfigNetworkRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        val future = networkTask.execute(input = appConfigNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = AppConfigWrapper::class.java)
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
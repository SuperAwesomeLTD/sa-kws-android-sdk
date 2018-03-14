package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AppConfigWrapper
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest
import tv.superawesome.protobufs.features.config.IConfigService
import tv.superawesome.protobufs.models.config.IConfigModel
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 14/03/2018.
 */
@PublishedApi
internal class ConfigProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : IConfigService {

    override fun getConfig(callback: (config: IConfigModel?, error: Throwable?) -> Unit) {
        val appConfigNetworkRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        networkTask.execute(input = appConfigNetworkRequest) { appConfigNetworkResponse ->

            // network success case
            if (appConfigNetworkResponse.response != null && appConfigNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = appConfigNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val appConfigResponseObject = parseTask.execute<AppConfigWrapper>(input = parseRequest,
                        clazz = AppConfigWrapper::class.java)

                //
                // send callback
                val error = if (appConfigResponseObject != null) null else Throwable("Error - not found valid app config")
                callback(appConfigResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, appConfigNetworkResponse.error)
            }
        }
    }


}
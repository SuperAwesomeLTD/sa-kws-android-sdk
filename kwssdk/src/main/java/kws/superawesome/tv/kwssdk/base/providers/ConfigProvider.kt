package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AppConfigWrapper
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest
import org.json.JSONException
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
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask), IConfigService {

    override fun getConfig(callback: (config: IConfigModel?, error: Throwable?) -> Unit) {

        val appConfigNetworkRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        networkTask.execute(input = appConfigNetworkRequest) { payload ->

            // network success case
            if (payload.success && payload.response != null) {

                val parseTask = ParseJsonTask()
                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val result = parseTask.execute<AppConfigWrapper>(input = parseRequest,
                        clazz = AppConfigWrapper::class.java)

                //parse error
                if (result == null) {

                    val error = JSONException(AppConfigWrapper::class.java.toString())
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


}
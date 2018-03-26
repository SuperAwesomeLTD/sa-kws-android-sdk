package kws.superawesome.tv.kwssdk.base.providers.internal

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.providers.Provider
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 16/03/2018.
 */
@PublishedApi
internal class TempTokenProvider
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask) {

    fun getTempAccessToken(callback: (loginAuthResponse: LoginAuthResponse?, error: Throwable?) -> Unit) {

        val getTempAccessTokenNetworkRequest = TempAccessTokenRequest(
                environment = environment,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        val future = networkTask.execute(input = getTempAccessTokenNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = LoginAuthResponse::class.java)
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
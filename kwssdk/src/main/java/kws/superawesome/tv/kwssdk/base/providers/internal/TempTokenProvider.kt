package kws.superawesome.tv.kwssdk.base.providers.internal

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.providers.Provider
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest
import org.json.JSONException
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
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

        networkTask.execute(input = getTempAccessTokenNetworkRequest) { payload ->

            // network success case
            if (payload.success && payload.response != null) {

                val parseTask = ParseJsonTask()
                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val result = parseTask.execute<LoginAuthResponse>(input = parseRequest,
                        clazz = LoginAuthResponse::class.java)

                //parse error
                if (result == null) {

                    val error = JSONException(LoginAuthResponse::class.java.toString())
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
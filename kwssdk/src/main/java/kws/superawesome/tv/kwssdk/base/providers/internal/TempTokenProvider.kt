package kws.superawesome.tv.kwssdk.base.providers.internal

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.providers.Provider
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest
import kws.superawesome.tv.kwssdk.base.services.internal.TempTokenService
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
    : Provider(environment = environment, networkTask = networkTask), TempTokenService {

    override fun getTempAccessToken(callback: (loginAuthResponse: LoginAuthResponse?, error: Throwable?) -> Unit) {

        val getTempAccessTokenNetworkRequest = TempAccessTokenRequest(
                environment = environment,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        networkTask.execute(input = getTempAccessTokenNetworkRequest) { payload ->

            // network success case
            if (payload.success && payload.response != null) {

                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val parseTask = ParseJsonTask()
                val result = parseTask.execute<LoginAuthResponse>(input = parseRequest,
                        clazz = LoginAuthResponse::class.java)
                //
                //send callback
                val error = if (result != null) null else Throwable("Error - couldn't parse JWT")
                callback(result, error)

            }
            //
            // network failure
            else if(payload.error != null){
                val error = super.parseServerError(serverError = payload.error)
                callback(null, error)
            }
            //
            // unknown error
            else{
                val error = SDKException()
                callback(null, error)
            }
        }
    }


}
package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.androidbaselib.network.NetworkURLEncodedTask
import kws.superawesome.tv.androidbaselib.parsejson.ParseJsonRequest
import kws.superawesome.tv.androidbaselib.parsejson.ParseJsonTask
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest
import kws.superawesome.tv.kwssdk.base.responses.BaseAuthResponse
import kws.superawesome.tv.kwssdk.base.services.LoginService
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata

/**
 * Created by guilherme.mota on 08/12/2017.
 */
@PublishedApi
internal class LoginProvider(val environment: KWSNetworkEnvironment) : LoginService {


    override fun loginUser(username: String,
                           password: String,
                           callback: (user: LoggedUser?, error: Throwable?) -> Unit) {


        val networkRequest = LoginUserRequest(
                environment = environment,
                username = username,
                password = password,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)
        val networkTask = NetworkURLEncodedTask()
        networkTask.execute(input = networkRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val authResponse = parseTask.execute<BaseAuthResponse>(input = parseRequest) ?: BaseAuthResponse()
                var token = authResponse.sessionToken

                token?.let {
                    //if we have a valid token
                    val metadata = KWSMetadata.processMetadata(it)

                    if (metadata != null && metadata.isValid()) {
                        val loggedUser = LoggedUser(token = it, kwsMetaData = metadata)
                        callback(loggedUser, null)
                    } else {
                        callback(null, Throwable("Invalid token"))
                    }
                }
                        ?: callback(null, Throwable(rawString))

            }
            // network error case
            else {
                callback(null, networkError)
            }
        }


    }


}
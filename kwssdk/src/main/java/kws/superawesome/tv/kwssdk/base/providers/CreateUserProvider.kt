package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.androidbaselib.network.NetworkJSONTask
import kws.superawesome.tv.androidbaselib.network.NetworkURLEncodedTask
import kws.superawesome.tv.androidbaselib.parsejson.ParseJsonRequest
import kws.superawesome.tv.androidbaselib.parsejson.ParseJsonTask
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.requests.CreateUserRequest
import kws.superawesome.tv.kwssdk.base.requests.GetTempAccessTokenRequest
import kws.superawesome.tv.kwssdk.base.responses.BaseAuthResponse
import kws.superawesome.tv.kwssdk.base.responses.CreateUserResponse
import kws.superawesome.tv.kwssdk.base.services.CreateUserService
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata

/**
 * Created by guilherme.mota on 12/12/2017.
 */

@PublishedApi
internal class CreateUserProvider(val environment: KWSNetworkEnvironment) : CreateUserService {


    override fun createuser(username: String,
                            password: String,
                            dateOfBirth: String,
                            country: String,
                            parentEmail: String,
                            callback: (user: LoggedUser?, error: Throwable?) -> Unit) {

        getTempAccessToken(environment = environment) { authToken: String?, networkError: Throwable? ->

            if (authToken != null && networkError == null) {
                val metadata = KWSMetadata.processMetadata(authToken)
                val appId = metadata.appId

                //Creation of user with temp access token
                doUserCreation(environment, username, password, dateOfBirth, country, parentEmail, appId, authToken, callback)
            } else {
                //
                // network failure
                callback(null, networkError)
            }

        }
    }


    private fun getTempAccessToken(environment: KWSNetworkEnvironment,
                                   callback: (authToken: String?, error: Throwable?) -> Unit) {

        val networkGetTempAccessTokenRequest = GetTempAccessTokenRequest(
                environment = environment,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        val networkGetTemAccessTokenTask = NetworkURLEncodedTask()

        networkGetTemAccessTokenTask.execute(input = networkGetTempAccessTokenRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val authResponse = parseTask.execute<BaseAuthResponse>(input = parseRequest) ?: BaseAuthResponse()
                val token = authResponse.sessionToken

                //
                // send callback
                token?.let {
                    callback(token, null)
                }
                        ?: callback(null, Throwable("Error parsing JWT token"))

            }
            //
            // network failure
            else {
                callback(null, networkError)
            }
        }
    }

    private fun doUserCreation(environment: KWSNetworkEnvironment,
                               username: String,
                               password: String,
                               dateOfBirth: String,
                               country: String,
                               parentEmail: String,
                               appId: Int,
                               token: String,
                               callback: (user: LoggedUser?, error: Throwable?) -> Unit) {


        val networkCreateUserRequest = CreateUserRequest(
                environment = environment,
                username = username,
                password = password,
                dateOfBirth = dateOfBirth,
                country = country,
                parentEmail = parentEmail,
                token = token,
                appID = appId)

        val networkCreateUserTask = NetworkJSONTask()

        networkCreateUserTask.execute(input = networkCreateUserRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val authResponse = parseTask.execute<CreateUserResponse>(input = parseRequest) ?: CreateUserResponse()
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

            } else {
                //
                // network failure
                callback(null, networkError)
            }
        }
    }

}
package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.androidbaselib.network.NetworkEnvironment
import kws.superawesome.tv.androidbaselib.network.NetworkTask
import kws.superawesome.tv.androidbaselib.parsejson.ParseJsonRequest
import kws.superawesome.tv.androidbaselib.parsejson.ParseJsonTask
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest
import kws.superawesome.tv.kwssdk.base.responses.BaseAuthResponse
import kws.superawesome.tv.kwssdk.base.services.LoginService

/**
 * Created by guilherme.mota on 08/12/2017.
 */
@PublishedApi
internal class LoginProvider(val environment: NetworkEnvironment) : LoginService {


    override fun loginUser(username: String,
                           password: String,
                           callback: (user: LoggedUser?, error: Throwable?) -> Unit) {


        val networkRequest = LoginUserRequest(environment = environment, username = username, password = password)
        val networkTask = NetworkTask()
        networkTask.execute(input = networkRequest) { rawString, networkError ->

            //
            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val authResponse = parseTask.execute<BaseAuthResponse>(input = parseRequest) ?: BaseAuthResponse()
                val token = authResponse.sessionToken

                val errorMessage: String? = with(authResponse) {
                    if (userBanned) "User banned"
                    else if (usernameForbidden) "Username forbidden"
                    else if (badCredentials) "Bad credentials"
                    else if (sessionToken == null) "Bad or invalid token"
                    else null
                }

                val loginError = errorMessage?.let { Throwable(errorMessage) }
                val loggedUser = token?.let { LoggedUser(userId = "", token = token) }

                callback(loggedUser, loginError)
            }
            //
            // network error case
            else {
                callback(null, networkError)
            }
        }


    }


}
package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.services.LoginService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask


/**
 * Created by guilherme.mota on 08/12/2017.
 */
@PublishedApi
internal class LoginProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : LoginService {

    override fun loginUser(username: String,
                           password: String,
                           callback: (user: LoginAuthResponse?, error: Throwable?) -> Unit) {


        val loginUserNetworkRequest = LoginUserRequest(
                environment = environment,
                username = username,
                password = password,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        networkTask.execute(input = loginUserNetworkRequest) { loginUserNetworkResponse ->

            //
            // network success case
            if (loginUserNetworkResponse.response != null && loginUserNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = loginUserNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val loginResponseObject = parseTask.execute<LoginAuthResponse>(input = parseRequest, clazz = LoginAuthResponse::class.java)

                //
                //send callback
                val error = if (loginResponseObject != null) null else Throwable("Error - not valid login")
                callback(loginResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, loginUserNetworkResponse.error)
            }
        }
    }

}
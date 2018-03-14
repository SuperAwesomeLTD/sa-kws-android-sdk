package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AuthUserResponse
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.requests.CreateUserRequest
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata
import tv.superawesome.protobufs.features.auth.IAuthService
import tv.superawesome.protobufs.models.auth.ILoggedUserModel
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Request
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 12/12/2017.
 */
@PublishedApi
internal class AuthProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : IAuthService {

    override fun loginUser(username: String, password: String, callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {
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

    override fun createUser(username: String, password: String, timeZone: String?, dataOfBirth: String?, country: String?, parentEmail: String?, callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {

        getTempAccessToken(environment = environment) { loginAuthResponse: LoginAuthResponse?, networkError: Throwable? ->

            if (loginAuthResponse?.token != null && networkError == null) {
                val token = loginAuthResponse.token

                val base64req = ParseBase64Request(base64String = token)
                val base64Task = ParseBase64Task()
                val metadataJson = base64Task.execute(input = base64req)

                val parseJsonReq = ParseJsonRequest(rawString = metadataJson)
                val parseJsonTask = ParseJsonTask()
                val metadata = parseJsonTask.execute(input = parseJsonReq, clazz = KWSMetadata::class.java)

                if (metadata != null) {

                    val appId = metadata.appId

                    if (dataOfBirth != null && country != null && parentEmail != null) {

                        //Creation of user with temp access token
                        doUserCreation(environment = environment, username = username, password = password,
                                dateOfBirth = dataOfBirth, country = country, parentEmail = parentEmail,
                                appId = appId, token = token, callback = callback)

                    } else {
                        callback(null, Throwable("Error with fields being null"))
                    }
                } else {
                    callback(null, Throwable("Error getting the metadata"))
                }
            } else {
                //
                // network failure
                callback(null, networkError)
            }

        }
    }


    private fun getTempAccessToken(environment: KWSNetworkEnvironment,
                                   callback: (loginAuthResponse: LoginAuthResponse?, error: Throwable?) -> Unit) {

        val getTempAccessTokenNetworkRequest = TempAccessTokenRequest(
                environment = environment,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        networkTask.execute(input = getTempAccessTokenNetworkRequest) { getTempAccessTokenNetworkResponse ->

            // network success case
            if (getTempAccessTokenNetworkResponse.response != null && getTempAccessTokenNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = getTempAccessTokenNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getTemAccessResponseObject = parseTask.execute<LoginAuthResponse>(input = parseRequest,
                        clazz = LoginAuthResponse::class.java)
                //
                //send callback
                val error = if (getTemAccessResponseObject != null) null else Throwable("Error - couldn't parse JWT")
                callback(getTemAccessResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, getTempAccessTokenNetworkResponse.error)
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
                               callback: (createdUserResponse: AuthUserResponse?, error: Throwable?) -> Unit) {


        val createUserNetworkRequest = CreateUserRequest(
                environment = environment,
                username = username,
                password = password,
                dateOfBirth = dateOfBirth,
                country = country,
                parentEmail = parentEmail,
                token = token,
                appID = appId)

        networkTask.execute(input = createUserNetworkRequest) { createUserNetworkResponse ->

            // network success case
            if (createUserNetworkResponse.response != null && createUserNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = createUserNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val createUserResponseObject = parseTask.execute<AuthUserResponse>(input = parseRequest,
                        clazz = AuthUserResponse::class.java)

                val error = if (createUserResponseObject != null) null else Throwable("Error - invalid create user")

                //
                //send callback
                callback(createUserResponseObject, error)

            } else {
                //
                // network failure
                callback(null, createUserNetworkResponse.error)
            }
        }
    }


}
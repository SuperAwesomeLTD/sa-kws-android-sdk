package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AuthUserResponse
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.models.internal.TokenData
import kws.superawesome.tv.kwssdk.base.providers.internal.TempTokenProvider
import kws.superawesome.tv.kwssdk.base.requests.CreateUserRequest
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest
import org.json.JSONException
import tv.superawesome.protobufs.features.auth.IAuthService
import tv.superawesome.protobufs.models.auth.ILoggedUserModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 12/12/2017.
 */
@PublishedApi
internal class AuthProvider
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask(),
            private val tempTokenProvider: TempTokenProvider = TempTokenProvider(
                    environment = environment, networkTask = networkTask))
    : Provider(environment = environment, networkTask = networkTask), IAuthService {

    override fun loginUser(username: String, password: String, callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {

        val loginUserNetworkRequest = LoginUserRequest(
                environment = environment,
                username = username,
                password = password,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        val future = networkTask.execute(input = loginUserNetworkRequest)

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

    override fun createUser(username: String, password: String, timeZone: String?,
                            dateOfBirth: String?, country: String?, parentEmail: String?,
                            callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {

        tempTokenProvider.getTempAccessToken { loginAuthResponse: LoginAuthResponse?, networkError: Throwable? ->

            if (loginAuthResponse?.token != null && networkError == null) {
                val token = loginAuthResponse.token

                val base64task = ParseBase64Task()
                val parse = ParseJsonTask(type = TokenData::class.java)
                val tokenResult = base64task.execute(input = token).then(parse::execute)

                when (tokenResult) {

                    is Result.success -> {

                        tokenResult.value.appId?.let {

                            if (dateOfBirth != null && country != null && parentEmail != null) {

                                //Creation of user with temp access token
                                doUserCreation(environment = environment, username = username, password = password,
                                        dateOfBirth = dateOfBirth, country = country, parentEmail = parentEmail,
                                        appId = it, token = token, callback = callback)

                            } else {
                                callback(null, SDKException())
                            }
                        } ?: run{
                            callback(null, SDKException())
                        }
                    }

                    is Result.error -> {
                        val serverError = parseServerError(error = tokenResult.error)
                        callback(null, serverError)
                    }

                }
            } else {
                //
                // network failure
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

        val future = networkTask.execute(input = createUserNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(AuthUserResponse::class.java)
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
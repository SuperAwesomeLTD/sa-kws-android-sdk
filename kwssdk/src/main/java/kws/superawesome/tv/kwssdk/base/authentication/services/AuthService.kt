package kws.superawesome.tv.kwssdk.base.authentication.services

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.authentication.models.AuthUserResponseModel
import kws.superawesome.tv.kwssdk.base.authentication.models.LoginAuthResponseModel
import kws.superawesome.tv.kwssdk.base.authentication.requests.CreateUserRequest
import kws.superawesome.tv.kwssdk.base.authentication.requests.LoginUserRequest
import kws.superawesome.tv.kwssdk.base.authentication.requests.TempAccessTokenRequest
import kws.superawesome.tv.kwssdk.base.common.services.AbstractService
import kws.superawesome.tv.kwssdk.base.internal.TokenData
import tv.superawesome.protobufs.authentication.models.ILoggedUserModel
import tv.superawesome.protobufs.authentication.services.IAuthService
import tv.superawesome.samobilebase.base64.ParseBase64Task
import tv.superawesome.samobilebase.common.result.Result
import tv.superawesome.samobilebase.json.ParseJsonTask
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 12/12/2017.
 */
@PublishedApi
internal class AuthService
@JvmOverloads
constructor(override val environment: NetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : AbstractService(environment = environment, networkTask = networkTask), IAuthService {

    override fun loginUser(username: String, password: String, callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {

        val loginUserNetworkRequest = LoginUserRequest(
                environment = environment,
                username = username,
                password = password,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        val parseTask = ParseJsonTask(type = LoginAuthResponseModel::class.java)
        val future = networkTask.execute(input = loginUserNetworkRequest)
                .map { result -> result.then(parseTask::execute) }

        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> callback(networkResult.value, null)
                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }
        }
    }

    override fun createUser(username: String, password: String, timeZone: String?,
                            dateOfBirth: String?, country: String?, parentEmail: String?,
                            callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {


        val dobValue = dateOfBirth ?: ""
        val countryValue = country ?: ""
        val parentEmailValue = parentEmail ?: ""

        val getTempAccessTokenNetworkRequest = TempAccessTokenRequest(
                environment = environment,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        val parseTask = ParseJsonTask(LoginAuthResponseModel::class.java)
        val future = networkTask
                .execute(input = getTempAccessTokenNetworkRequest)
                .map { result -> result.then(parseTask::execute) }


        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> {

                    val token = networkResult.value.token

                    val base64task = ParseBase64Task()
                    val parseTask2 = ParseJsonTask(type = TokenData::class.java)
                    val tokenResult = base64task.execute(input = networkResult.value.token).then(parseTask2::execute)

                    when (tokenResult) {

                        is Result.success -> {

                            doUserCreation(environment = environment, username = username, password = password,
                                    dateOfBirth = dobValue, country = countryValue, parentEmail = parentEmailValue,
                                    appId = tokenResult.value.appId, token = token, callback = callback)

                        }

                        is Result.error -> {
                            val serverError = parseServerError(error = tokenResult.error)
                            callback(null, serverError)
                        }

                    }

                }

                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }
        }
    }

    private fun doUserCreation(environment: NetworkEnvironment,
                               username: String,
                               password: String,
                               dateOfBirth: String,
                               country: String,
                               parentEmail: String,
                               appId: Int,
                               token: String,
                               callback: (createdUserResponseModel: AuthUserResponseModel?, error: Throwable?) -> Unit) {


        val createUserNetworkRequest = CreateUserRequest(
                environment = environment,
                username = username,
                password = password,
                dateOfBirth = dateOfBirth,
                country = country,
                parentEmail = parentEmail,
                token = token,
                appID = appId)

        val parseTask = ParseJsonTask(AuthUserResponseModel::class.java)
        val future = networkTask.execute(input = createUserNetworkRequest)
                .map { result -> result.then(parseTask::execute) }

        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> callback(networkResult.value, null)
                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }
        }
    }


}
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

        networkTask.execute(input = loginUserNetworkRequest) { payload ->

            //
            // network success case
            if (payload.success && payload.response != null) {

                val parseTask = ParseJsonTask()
                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val result = parseTask.execute<LoginAuthResponse>(input = parseRequest, clazz = LoginAuthResponse::class.java)

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
            // server error case
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

    override fun createUser(username: String, password: String, timeZone: String?,
                            dateOfBirth: String?, country: String?, parentEmail: String?,
                            callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {

        tempTokenProvider.getTempAccessToken { loginAuthResponse: LoginAuthResponse?, networkError: Throwable? ->

            if (loginAuthResponse?.token != null && networkError == null) {
                val token = loginAuthResponse.token

                val base64Task = ParseBase64Task()
                val base64req = ParseBase64Request(base64String = token)
                val metadataJson = base64Task.execute(input = base64req)

                val parseJsonTask = ParseJsonTask()
                val parseJsonReq = ParseJsonRequest(rawString = metadataJson)
                val metadata = parseJsonTask.execute<TokenData>(input = parseJsonReq, clazz = TokenData::class.java)

                if (metadata?.appId != null) {

                    val appId = metadata.appId

                    if (dateOfBirth != null && country != null && parentEmail != null) {

                        //Creation of user with temp access token
                        doUserCreation(environment = environment, username = username, password = password,
                                dateOfBirth = dateOfBirth, country = country, parentEmail = parentEmail,
                                appId = appId, token = token, callback = callback)

                    } else {
                        callback(null, SDKException())
                    }
                } else {
                    val error = JSONException(TokenData::class.java.toString())
                    callback(null, error)
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

        networkTask.execute(input = createUserNetworkRequest) { payload ->

            // network success case
            if (payload.success && payload.response != null) {

                val parseTask = ParseJsonTask()
                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val result = parseTask.execute<AuthUserResponse>(input = parseRequest,
                        clazz = AuthUserResponse::class.java)

                //parse error
                if (result == null) {

                    val error = JSONException(AuthUserResponse::class.java.toString())
                    callback(null, error)

                } else {

                    //send callback
                    callback(result, null)

                }


            } else if (payload.error != null) {
                //
                // server error case
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
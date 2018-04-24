package kws.superawesome.tv.kwssdk.base.authentication.services

import android.app.Activity
import android.content.Intent
import android.net.Uri
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.authentication.models.LoginAuthResponseModel
import kws.superawesome.tv.kwssdk.base.authentication.requests.OAuthUserTokenRequest
import kws.superawesome.tv.kwssdk.base.authentication.web_auth.OAuthCodeTask
import kws.superawesome.tv.kwssdk.base.authentication.web_auth.WebAuthController
import kws.superawesome.tv.kwssdk.base.common.services.AbstractService
import kws.superawesome.tv.kwssdk.base.internal.LoggedUserModel
import kws.superawesome.tv.kwssdk.base.internal.SDKException
import kws.superawesome.tv.kwssdk.base.internal.TokenData
import tv.superawesome.protobufs.authentication.models.ILoggedUserModel
import tv.superawesome.protobufs.authentication.services.ISingleSignOnService
import tv.superawesome.samobilebase.base64.ParseBase64Task
import tv.superawesome.samobilebase.common.result.Result
import tv.superawesome.samobilebase.json.ParseJsonTask
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 26/01/2018.
 */
@PublishedApi
internal class SingleSignOnService
@JvmOverloads
constructor(override val environment: NetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : AbstractService(environment = environment, networkTask = networkTask), ISingleSignOnService {


    override fun signOn(url: String, parent: Activity, callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit) {
        val oAuthCodeGenerator = OAuthCodeTask()
        val oAuthDataClass = oAuthCodeGenerator.execute(Any())

        getAuthCode(environment = environment,
                singleSignOnUrl = url,
                parent = parent, codeChallenge = oAuthDataClass.codeChallenge,
                codeChallengeMethod = oAuthDataClass.codeChallengeMethod) { authCode: String?, networkError: Throwable? ->

            if (authCode != null && !authCode.isEmpty()) {

                getAccessToken(environment = environment, authCode = authCode, codeVerifier = oAuthDataClass.codeVerifier, callback = callback)

            } else {
                //
                //network failure
                callback(null, networkError)
            }


        }
    }


    private fun getAuthCode(environment: NetworkEnvironment, singleSignOnUrl: String,
                            parent: Activity,
                            codeChallenge: String,
                            codeChallengeMethod: String,
                            callback: (String?, Throwable?) -> Unit) {

        // TODO("Maybe replace this with a custom Request object")
        val endpoint = "oauth"
        val clientId = environment.appID
        val packageName = parent.packageName
        val url = "$singleSignOnUrl$endpoint?clientId=$clientId&codeChallenge=$codeChallenge&codeChallengeMethod=$codeChallengeMethod&redirectUri=$packageName://"
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        parent.startActivity(intent)

        // TODO("Again, somewhat hacky here! But don't have a proper solution yet")
        WebAuthController.callback = { code ->


            val error = if (!code.isNullOrEmpty()) null else Throwable("Error - invalid code")
            //
            //send callback
            callback(code, error)
        }
    }

    private fun getAccessToken(environment: NetworkEnvironment, authCode: String,
                               codeVerifier: String, callback: (user: LoggedUserModel?, error: Throwable?) -> Unit) {

        val oAuthTokenNetworkRequest = OAuthUserTokenRequest(
                environment = environment,
                clientID = environment.appID,
                authCode = authCode,
                codeVerifier = codeVerifier,
                clientSecret = environment.mobileKey
        )

        val parseTask = ParseJsonTask(type = LoginAuthResponseModel::class.java)
        val future = networkTask.execute(input = oAuthTokenNetworkRequest)
                .map { result -> result.then(parseTask::execute) }

        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> {
                    val token = networkResult.value.token

                    val base64task = ParseBase64Task()
                    val parse2 = ParseJsonTask(type = TokenData::class.java)
                    val tokenResult = base64task.execute(input = token).then(parse2::execute)

                    when (tokenResult) {
                        is Result.success -> {

                            tokenResult.value.userId?.let {

                                val user = LoggedUserModel(token = token, tokenData = tokenResult.value, id = it)
                                callback(user, null)

                            } ?: run {

                                callback(null, SDKException())

                            }

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

}
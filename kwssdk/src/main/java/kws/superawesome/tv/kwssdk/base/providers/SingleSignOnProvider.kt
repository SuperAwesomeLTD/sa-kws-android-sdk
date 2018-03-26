package kws.superawesome.tv.kwssdk.base.providers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.models.internal.LoggedUser
import kws.superawesome.tv.kwssdk.base.models.internal.TokenData
import kws.superawesome.tv.kwssdk.base.requests.OAuthUserTokenRequest
import kws.superawesome.tv.kwssdk.base.webauth.KWSWebAuthResponse
import kws.superawesome.tv.kwssdk.base.webauth.OAuthCodeTask
import tv.superawesome.protobufs.features.auth.ISingleSignOnService
import tv.superawesome.protobufs.models.auth.ILoggedUserModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 26/01/2018.
 */
@PublishedApi
internal class SingleSignOnProvider
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask), ISingleSignOnService {


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


    private fun getAuthCode(environment: KWSNetworkEnvironment, singleSignOnUrl: String,
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
        KWSWebAuthResponse.callback = { code ->


            val error = if (!code.isNullOrEmpty()) null else Throwable("Error - invalid code")
            //
            //send callback
            callback(code, error)
        }
    }

    private fun getAccessToken(environment: KWSNetworkEnvironment, authCode: String,
                               codeVerifier: String, callback: (user: LoggedUser?, error: Throwable?) -> Unit) {

        val oAuthTokenNetworkRequest = OAuthUserTokenRequest(
                environment = environment,
                clientID = environment.appID,
                authCode = authCode,
                codeVerifier = codeVerifier,
                clientSecret = environment.mobileKey
        )

        val future = networkTask.execute(input = oAuthTokenNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = LoginAuthResponse::class.java)
            val result = networkResult.then(parse::execute)

            when (result) {

                is Result.success -> {
                    val token = result.value.token

                    val base64task = ParseBase64Task()
                    val parse2 = ParseJsonTask(type = TokenData::class.java)
                    val tokenResult = base64task.execute(input = token).then(parse2::execute)

                    when (tokenResult) {
                        is Result.success -> {

                            tokenResult.value.userId?.let {

                                val user = LoggedUser(
                                        token = token,
                                        tokenData = tokenResult.value,
                                        id = it)

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
                    val serverError = parseServerError(error = result.error)
                    callback(null, serverError)
                }
            }

        }

    }

}
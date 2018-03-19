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
import org.json.JSONException
import tv.superawesome.protobufs.features.auth.ISingleSignOnService
import tv.superawesome.protobufs.models.auth.ILoggedUserModel
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Request
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
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
        val oAuthDataClass = oAuthCodeGenerator.execute()

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

        networkTask.execute(input = oAuthTokenNetworkRequest) { payload ->

            if (payload.success && payload.response != null) {

                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val parseTask = ParseJsonTask()
                val result = parseTask.execute<LoginAuthResponse>(input = parseRequest,
                        clazz = LoginAuthResponse::class.java)

                //parse error
                if (result == null) {

                    val error = JSONException(LoginAuthResponse::class.java.toString())
                    callback(null, error)

                } else {

                    val base64Task = ParseBase64Task()
                    val base64req = ParseBase64Request(base64String = result?.token)
                    val metadataJson = base64Task.execute(input = base64req)

                    val parseJsonTask = ParseJsonTask()
                    val parseJsonReq = ParseJsonRequest(rawString = metadataJson)
                    val tokenData = parseJsonTask.execute(input = parseJsonReq, clazz = TokenData::class.java)

                    //parse error
                    if (tokenData == null) {

                        val error = JSONException(TokenData::class.java.toString())
                        callback(null, error)

                    } else {

                        if (tokenData.userId != null) {

                            val loggedUser = LoggedUser(token = result.token, tokenData = tokenData, id = tokenData.userId)
                            callback(loggedUser, null)

                        }

                    }


                }
            }
            //
            // network failure
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


}
package kws.superawesome.tv.kwssdk.base.providers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.internal.LoggedUser
import kws.superawesome.tv.kwssdk.base.requests.OAuthUserTokenRequest
import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse
import kws.superawesome.tv.kwssdk.base.services.AuthService
import kws.superawesome.tv.kwssdk.base.webauth.KWSWebAuthResponse
import kws.superawesome.tv.kwssdk.base.webauth.OAuthCodeTask
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Request
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 26/01/2018.
 */
@PublishedApi
internal class AuthProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : AuthService {
    override fun authUser(singleSignOnUrl: String,
                          parent: Activity,
                          callback: (user: LoggedUser?, error: Throwable?) -> Unit) {

        val oAuthCodeGenerator = OAuthCodeTask()
        val oAuthDataClass = oAuthCodeGenerator.execute()

        getAuthCode(environment = environment,
                singleSignOnUrl = singleSignOnUrl,
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

        networkTask.execute(input = oAuthTokenNetworkRequest) { oAuthTokenNetworkResponse ->

            if (oAuthTokenNetworkResponse.response != null && oAuthTokenNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = oAuthTokenNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val oauthResponseObject = parseTask.execute<LoginAuthResponse>(input = parseRequest,
                        clazz = LoginAuthResponse::class.java)

                val base64req = ParseBase64Request(base64String = oauthResponseObject?.token)
                val base64Task = ParseBase64Task()
                val metadataJson = base64Task.execute(input = base64req)

                val parseJsonReq = ParseJsonRequest(rawString = metadataJson)
                val parseJsonTask = ParseJsonTask()
                val metadata = parseJsonTask.execute(input = parseJsonReq, clazz = KWSMetadata::class.java)


                val error = if (metadata != null && metadata.isValid) null else Throwable("Error - invalid oauth user")


                val loggedUser = if (error == null
                        && metadata != null
                        && metadata.isValid
                        && oauthResponseObject != null
                        && oauthResponseObject.token != null) {

                    LoggedUser(token = oauthResponseObject.token, kwsMetaData = metadata)

                } else {
                    null
                }

                //
                //send callback
                callback(loggedUser, error)

            } else {
                //
                //network error
                callback(null, oAuthTokenNetworkResponse.error)
            }

        }


    }

}
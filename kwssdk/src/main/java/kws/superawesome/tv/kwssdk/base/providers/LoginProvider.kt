package kws.superawesome.tv.kwssdk.base.providers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.requests.LoginUserRequest
import kws.superawesome.tv.kwssdk.base.responses.Login
import kws.superawesome.tv.kwssdk.base.services.LoginService
import kws.superawesome.tv.kwssdk.base.webauth.KWSWebAuthResponse
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Request
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
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
                           callback: (user: Login?, error: Throwable?) -> Unit) {


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
                val loginResponseObject = parseTask.execute<Login>(input = parseRequest, clazz = Login::class.java)

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

    override fun authUser(singleSignOnUrl: String,
                          parent: Activity,
                          callback: (user: LoggedUser?, error: Throwable?) -> Unit) {

        // TODO("Maybe replace this with a custom Request object")
        val endpoint = "oauth"
        val clientId = environment.appID
        val packageName = parent.packageName
        val url = "$singleSignOnUrl$endpoint?clientId=$clientId&redirectUri=$packageName://"
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        parent.startActivity(intent)

        // TODO("Again, somewhat hacky here! But don't have a proper solution yet")
        KWSWebAuthResponse.callback = { token ->

            if (token != null) {
                //if we have a valid token
                val base64req = ParseBase64Request(base64String = token)
                val base64Task = ParseBase64Task()
                val metadataJson = base64Task.execute(input = base64req)

                val parseJsonReq = ParseJsonRequest(rawString = metadataJson)
                val parseJsonTask = ParseJsonTask()
                val metadata = parseJsonTask.execute(input = parseJsonReq, clazz = KWSMetadata::class.java)

                if (metadata != null && metadata.isValid()) {
                    val loggedUser = LoggedUser(token = token, kwsMetaData = metadata)
                    callback(loggedUser, null)
                } else {
                    callback(null, Throwable("Invalid token"))
                }
            } else {
                callback(null, Throwable("OAuth token is null"))
            }
        }
    }
}
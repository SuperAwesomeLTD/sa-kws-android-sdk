package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.CreateUserRequest
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest
import kws.superawesome.tv.kwssdk.base.responses.CreateUser
import kws.superawesome.tv.kwssdk.base.responses.Login
import kws.superawesome.tv.kwssdk.base.services.CreateUserService
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Request
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 12/12/2017.
 */
@PublishedApi
internal class CreateUserProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : CreateUserService {


    override fun startCreateUserFlow(username: String,
                                     password: String,
                                     dateOfBirth: String,
                                     country: String,
                                     parentEmail: String,
                                     callback: (user: CreateUser?, error: Throwable?) -> Unit) {

        getTempAccessToken(environment = environment) { login: Login?, networkError: Throwable? ->

            if (login?.token != null && networkError == null) {
                val token = login.token

                val base64req = ParseBase64Request(base64String = token)
                val base64Task = ParseBase64Task()
                val metadataJson = base64Task.execute(input = base64req)

                val parseJsonReq = ParseJsonRequest(rawString = metadataJson)
                val parseJsonTask = ParseJsonTask()
                val metadata = parseJsonTask.execute(input = parseJsonReq, clazz = KWSMetadata::class.java)

                if (metadata != null) {

                    val appId = metadata.appId

                    //Creation of user with temp access token
                    doUserCreation(environment, username, password, dateOfBirth, country, parentEmail, appId, token, callback)
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


    fun getTempAccessToken(environment: KWSNetworkEnvironment,
                                    callback: (login: Login?, error: Throwable?) -> Unit) {

        val getTempAccessTokenNetworkRequest = TempAccessTokenRequest(
                environment = environment,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        networkTask.execute(input = getTempAccessTokenNetworkRequest) { getTempAccessTokenNetworkResponse ->

            // network success case
            if (getTempAccessTokenNetworkResponse.response != null && getTempAccessTokenNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = getTempAccessTokenNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getTemAccessResponseObject = parseTask.execute<Login>(input = parseRequest,
                        clazz = Login::class.java)
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

    fun doUserCreation(environment: KWSNetworkEnvironment,
                                username: String,
                                password: String,
                                dateOfBirth: String,
                                country: String,
                                parentEmail: String,
                                appId: Int,
                                token: String,
                                callback: (createdUser: CreateUser?, error: Throwable?) -> Unit) {


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
                val createUserResponseObject = parseTask.execute<CreateUser>(input = parseRequest,
                        clazz = CreateUser::class.java)

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
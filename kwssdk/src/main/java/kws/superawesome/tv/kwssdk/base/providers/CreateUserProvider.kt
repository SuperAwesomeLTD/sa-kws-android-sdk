package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.requests.CreateUserRequest
import kws.superawesome.tv.kwssdk.base.requests.TempAccessTokenRequest
import kws.superawesome.tv.kwssdk.base.responses.CreateUser
import kws.superawesome.tv.kwssdk.base.responses.Login
import kws.superawesome.tv.kwssdk.base.services.CreateUserService
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 12/12/2017.
 */

@PublishedApi
internal class CreateUserProvider(val environment: KWSNetworkEnvironment) : CreateUserService {


    override fun createUser(username: String,
                            password: String,
                            dateOfBirth: String,
                            country: String,
                            parentEmail: String,
                            callback: (user: CreateUser?, error: Throwable?) -> Unit) {

        getTempAccessToken(environment = environment) { login: Login?, networkError: Throwable? ->

            if (login?.token != null && networkError == null) {
                val token = login.token
                val metadata = KWSMetadata.processMetadata(token)
                val appId = metadata.appId

                //Creation of user with temp access token
                doUserCreation(environment, username, password, dateOfBirth, country, parentEmail, appId, token, callback)
            } else {
                //
                // network failure
                callback(null, networkError)
            }

        }
    }


    private fun getTempAccessToken(environment: KWSNetworkEnvironment,
                                   callback: (login: Login?, error: Throwable?) -> Unit) {

        val networkGetTempAccessTokenRequest = TempAccessTokenRequest(
                environment = environment,
                clientID = environment.appID,
                clientSecret = environment.mobileKey)

        val networkGetTemAccessTokenTask = NetworkTask()

        networkGetTemAccessTokenTask.execute(input = networkGetTempAccessTokenRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val authResponse = parseTask.execute<Login>(input = parseRequest,
                        clazz = Login::class.java)
                //
                //send callback
                val error = if (authResponse != null) null else Throwable("Error - couldn't parse JWT")
                callback(authResponse,error)

            }
            //
            // network failure
            else {
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
                               callback: (createdUser: CreateUser?, error: Throwable?) -> Unit) {


        val networkCreateUserRequest = CreateUserRequest(
                environment = environment,
                username = username,
                password = password,
                dateOfBirth = dateOfBirth,
                country = country,
                parentEmail = parentEmail,
                token = token,
                appID = appId)

        val networkCreateUserTask = NetworkTask()

        networkCreateUserTask.execute(input = networkCreateUserRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val createUserResponse = parseTask.execute<CreateUser>(input = parseRequest,
                        clazz = CreateUser::class.java)

                val error = if (createUserResponse != null) null else Throwable("Error - invalid create user")

                //
                //send callback
                callback(createUserResponse, error)

            } else {
                //
                // network failure
                callback(null, networkError)
            }
        }
    }

}
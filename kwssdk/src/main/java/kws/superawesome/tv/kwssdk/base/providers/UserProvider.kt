package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.InviteUserRequest
import kws.superawesome.tv.kwssdk.base.requests.UserDetailsRequest
import kws.superawesome.tv.kwssdk.base.requests.UserScoreRequest
import kws.superawesome.tv.kwssdk.base.responses.Score
import kws.superawesome.tv.kwssdk.base.responses.UserDetails
import kws.superawesome.tv.kwssdk.base.services.UserService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 03/01/2018.
 */
@PublishedApi
internal class UserProvider(val environment: KWSNetworkEnvironment) : UserService {

    override fun getUserDetails(userId: Int, token: String, callback: (userDetailsDetails: UserDetails?, error: Throwable?) -> Unit) {

        val getUserDetailsNetworkRequest = UserDetailsRequest(
                environment = environment,
                userId = userId,
                token = token
        )

        val getUserDetailsNetworkTask = NetworkTask()
        getUserDetailsNetworkTask.execute(input = getUserDetailsNetworkRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {
                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val getUserDetailsResponse = parseTask.execute<UserDetails>(input = parseRequest,
                        clazz = UserDetails::class.java)

                val error = if (getUserDetailsResponse == null) Throwable("Error getting user details") else null;
                callback(getUserDetailsResponse, error)

            }
            //
            // network failure
            else {
                callback(null, networkError)
            }

        }
    }

    override fun inviteUser(email: String, userId: Int, token: String, callback: (success: Boolean?, error: Throwable?) -> Unit) {

        val inviteUserRequest = InviteUserRequest(
                environment = environment,
                userId = userId,
                token = token,
                emailAddress = email
        )

        val inviteUserRequestNetworkTask = NetworkTask()
        inviteUserRequestNetworkTask.execute(input = inviteUserRequest) { rawString, networkError ->

            //
            //send callback
            callback(rawString.isNullOrEmpty() && networkError == null, networkError)

        }
    }


    override fun getScore(appId: Int, token: String, callback: (score: Score?, error: Throwable?) -> Unit) {


        val getUserScoreRequest = UserScoreRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        val getUserScoreRequestNetworkTask = NetworkTask()
        getUserScoreRequestNetworkTask.execute(input = getUserScoreRequest) { rawString, networkError ->


            if (rawString != null && networkError == null) {
                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val getScoreResponse = parseTask.execute<Score>(input = parseRequest,
                        clazz = Score::class.java)

                //
                //send callback
                val error = if (getScoreResponse == null) Throwable("Error getting user score") else null;
                callback(getScoreResponse, error)

            } else {
                //
                // network failure

                callback(null, networkError)
            }
        }


    }

}




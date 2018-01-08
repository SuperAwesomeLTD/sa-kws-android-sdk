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
        getUserDetailsNetworkTask.execute(input = getUserDetailsNetworkRequest) { getUserDetailsNetworkResponse ->

            // network success case
            if (getUserDetailsNetworkResponse.response != null && getUserDetailsNetworkResponse.error == null) {
                val parseRequest = ParseJsonRequest(rawString = getUserDetailsNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getUserDetailsResponseObject = parseTask.execute<UserDetails>(input = parseRequest,
                        clazz = UserDetails::class.java)

                val error = if (getUserDetailsResponseObject == null) Throwable("Error getting user details") else null;
                callback(getUserDetailsResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, getUserDetailsNetworkResponse.error)
            }

        }
    }

    override fun inviteUser(email: String, userId: Int, token: String, callback: (success: Boolean?, error: Throwable?) -> Unit) {

        val inviteUserNetworkRequest = InviteUserRequest(
                environment = environment,
                userId = userId,
                token = token,
                emailAddress = email
        )

        val inviteUserRequestNetworkTask = NetworkTask()
        inviteUserRequestNetworkTask.execute(input = inviteUserNetworkRequest) { inviteUserNetworkResponse ->

            //
            //send callback
            callback((inviteUserNetworkResponse.status == 200 || inviteUserNetworkResponse.status == 204)
                    && inviteUserNetworkResponse.error == null, inviteUserNetworkResponse.error)

        }
    }


    override fun getScore(appId: Int, token: String, callback: (score: Score?, error: Throwable?) -> Unit) {


        val getUserScoreNetworkRequest = UserScoreRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        val getUserScoreRequestNetworkTask = NetworkTask()
        getUserScoreRequestNetworkTask.execute(input = getUserScoreNetworkRequest) { getUserScoreNetworkResponse ->


            if (getUserScoreNetworkResponse.response != null && getUserScoreNetworkResponse.error == null) {
                val parseRequest = ParseJsonRequest(rawString = getUserScoreNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getScoreResponseObject = parseTask.execute<Score>(input = parseRequest,
                        clazz = Score::class.java)

                //
                //send callback
                val error = if (getScoreResponseObject == null) Throwable("Error getting user score") else null;
                callback(getScoreResponseObject, error)

            } else {
                //
                // network failure

                callback(null, getUserScoreNetworkResponse.error)
            }
        }


    }

}




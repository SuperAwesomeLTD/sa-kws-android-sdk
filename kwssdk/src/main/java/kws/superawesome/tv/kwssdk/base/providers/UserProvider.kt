package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.InviteUserRequest
import kws.superawesome.tv.kwssdk.base.requests.PermissionsRequest
import kws.superawesome.tv.kwssdk.base.requests.UserDetailsRequest
import kws.superawesome.tv.kwssdk.base.requests.UserScoreRequest
import kws.superawesome.tv.kwssdk.base.models.Score
import kws.superawesome.tv.kwssdk.base.models.UserDetails
import kws.superawesome.tv.kwssdk.base.services.UserService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 03/01/2018.
 */
@PublishedApi
internal class UserProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : UserService {

    override fun getUserDetails(userId: Int, token: String, callback: (userDetailsDetails: UserDetails?, error: Throwable?) -> Unit) {

        val getUserDetailsNetworkRequest = UserDetailsRequest(
                environment = environment,
                userId = userId,
                token = token
        )

        networkTask.execute(input = getUserDetailsNetworkRequest) { getUserDetailsNetworkResponse ->

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

        networkTask.execute(input = inviteUserNetworkRequest) { inviteUserNetworkResponse ->

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

        networkTask.execute(input = getUserScoreNetworkRequest) { getUserScoreNetworkResponse ->


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


    override fun requestPermissions(userId: Int, token: String, permissionsList: List<String>, callback: (success: Boolean, error: Throwable?) -> Unit) {


        val requestPermissionsNetworkRequest = PermissionsRequest(
                environment = environment,
                userId = userId,
                token = token,
                permissionsList = permissionsList
        )

        networkTask.execute(input = requestPermissionsNetworkRequest) { requestPermissionsNetworkResponse ->

            if (requestPermissionsNetworkResponse.response != null && requestPermissionsNetworkResponse.error == null) {

                //
                //send callback
                if (requestPermissionsNetworkResponse.status == 200
                        || requestPermissionsNetworkResponse.status == 204) {
                    callback(true, null)
                } else {
                    //
                    // we have a response, but something else went wrong
                    val payload = requestPermissionsNetworkResponse.response
                    val message = if (payload != null) payload else "Unknown network error"
                    val error = Throwable(message)
                    callback(false, error)
                }

            } else {
                //
                // network failure
                callback(false, requestPermissionsNetworkResponse.error)
            }


        }


    }

}




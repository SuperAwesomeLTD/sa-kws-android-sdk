package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.InviteUserRequest
import kws.superawesome.tv.kwssdk.base.requests.PermissionsRequest
import kws.superawesome.tv.kwssdk.base.requests.UserDetailsRequest
import kws.superawesome.tv.kwssdk.base.requests.UserScoreRequest
import kws.superawesome.tv.kwssdk.base.models.Score
import kws.superawesome.tv.kwssdk.base.models.UserDetails
import kws.superawesome.tv.kwssdk.base.services.UserService
import tv.superawesome.protobufs.features.user.IUserService
import tv.superawesome.protobufs.models.user.IUserDetailsModel
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
            private val networkTask: NetworkTask = NetworkTask()) : IUserService {


    override fun getUser(userId: Int, token: String, callback: (user: IUserDetailsModel?, error: Throwable?) -> Unit) {
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

    override fun updateUser(details: IUserDetailsModel, token: String, callback: (error: Throwable?) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




}




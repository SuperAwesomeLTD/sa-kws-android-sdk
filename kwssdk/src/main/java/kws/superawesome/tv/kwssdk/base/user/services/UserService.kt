package kws.superawesome.tv.kwssdk.base.user.services

import kws.superawesome.tv.kwssdk.base.common.AbstractService
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.user.models.UserDetailsModel
import kws.superawesome.tv.kwssdk.base.user.requests.UpdateUserDetailsRequest
import kws.superawesome.tv.kwssdk.base.user.requests.GetUserDetailsRequest
import tv.superawesome.protobufs.features.user.IUserService
import tv.superawesome.protobufs.models.user.IUserDetailsModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 03/01/2018.
 */
@PublishedApi
internal class UserService
@JvmOverloads
constructor(override val environment: NetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : AbstractService(environment = environment, networkTask = networkTask), IUserService {

    override fun getUser(userId: Int, token: String, callback: (user: IUserDetailsModel?, error: Throwable?) -> Unit) {
        val getUserDetailsNetworkRequest = GetUserDetailsRequest(
                environment = environment,
                userId = userId,
                token = token
        )

        val parseTask = ParseJsonTask(type = UserDetailsModel::class.java)
        val future = networkTask.execute(input = getUserDetailsNetworkRequest)
                .map { result -> result.then(parseTask::execute) }

        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> callback(networkResult.value, null)
                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(null, serverError)
                }

            }
        }
    }

    override fun updateUser(details: Map<String, Any>, userId: Int, token: String, callback: (error: Throwable?) -> Unit) {

        val updateUserDetailsNetworkRequest = UpdateUserDetailsRequest(
                environment = environment,
                userDetailsMap = details,
                userId = userId,
                token = token)

        val future = networkTask.execute(input = updateUserDetailsNetworkRequest)
        future.onResult { networkResult ->

            when (networkResult) {

                is Result.success -> callback(null)
                is Result.error -> {
                    val serverError = parseServerError(error = networkResult.error)
                    callback(serverError)
                }
            }
        }
    }
}

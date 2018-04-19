package kws.superawesome.tv.kwssdk.base.user

import kws.superawesome.tv.kwssdk.base.BaseService
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.user.models.UserDetails
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
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : BaseService(environment = environment, networkTask = networkTask), IUserService {

    override fun getUser(userId: Int, token: String, callback: (user: IUserDetailsModel?, error: Throwable?) -> Unit) {
        val getUserDetailsNetworkRequest = GetUserDetailsRequest(
                environment = environment,
                userId = userId,
                token = token
        )

        val parseTask = ParseJsonTask(type = UserDetails::class.java)
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


        val networkTask2 = NetworkTask()
        val future = networkTask2.execute(input = updateUserDetailsNetworkRequest)
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

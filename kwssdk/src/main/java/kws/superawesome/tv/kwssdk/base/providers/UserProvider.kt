package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.UserDetails
import kws.superawesome.tv.kwssdk.base.requests.UserDetailsRequest
import tv.superawesome.protobufs.features.user.IUserService
import tv.superawesome.protobufs.models.user.IUserDetailsModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 03/01/2018.
 */
@PublishedApi
internal class UserProvider
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask), IUserService {


    override fun getUser(userId: Int, token: String, callback: (user: IUserDetailsModel?, error: Throwable?) -> Unit) {
        val getUserDetailsNetworkRequest = UserDetailsRequest(
                environment = environment,
                userId = userId,
                token = token
        )

        val future = networkTask.execute(input = getUserDetailsNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = UserDetails::class.java)
            val result = networkResult.then(parse::execute)

            when (result) {

                is Result.success -> {
                    callback(result.value, null)
                }

                is Result.error -> {
                    val serverError = parseServerError(error = result.error)
                    callback(null, serverError)
                }
            }
        }
    }

    override fun updateUser(details: IUserDetailsModel, token: String, callback: (error: Throwable?) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}




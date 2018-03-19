package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.models.UserDetails
import kws.superawesome.tv.kwssdk.base.requests.UserDetailsRequest
import org.json.JSONException
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
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask), IUserService {


    override fun getUser(userId: Int, token: String, callback: (user: IUserDetailsModel?, error: Throwable?) -> Unit) {
        val getUserDetailsNetworkRequest = UserDetailsRequest(
                environment = environment,
                userId = userId,
                token = token
        )

        networkTask.execute(input = getUserDetailsNetworkRequest) { payload ->

            // network success case
            if (payload.success && payload.response != null) {

                val parseTask = ParseJsonTask()
                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val result = parseTask.execute<UserDetails>(input = parseRequest,
                        clazz = UserDetails::class.java)


                //parse error
                if (result == null) {

                    val error = JSONException(UserDetails::class.java.toString())
                    callback(null, error)

                } else {

                    //send callback
                    callback(result, null)

                }

            }
            //
            // network failure
            else if (payload.error != null) {
                val error = super.parseServerError(serverError = payload.error)
                callback(null, error)
            }
            //
            // unknown error
            else {
                val error = SDKException()
                callback(null, error)
            }

        }
    }

    override fun updateUser(details: IUserDetailsModel, token: String, callback: (error: Throwable?) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}




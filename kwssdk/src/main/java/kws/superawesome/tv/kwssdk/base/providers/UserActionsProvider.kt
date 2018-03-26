package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AppDataWrapper
import kws.superawesome.tv.kwssdk.base.models.HasTriggeredEvent
import kws.superawesome.tv.kwssdk.base.requests.*
import tv.superawesome.protobufs.features.user.IUserActionsService
import tv.superawesome.protobufs.models.appdata.IAppDataWrapperModel
import tv.superawesome.protobufs.models.score.IHasTriggeredEventModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.INetworkRequest
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 05/01/2018.
 */
@PublishedApi
internal class UserActionsProvider
@JvmOverloads
constructor(override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment, networkTask = networkTask), IUserActionsService {


    override fun getAppData(userId: Int, appId: Int, token: String, callback: (appData: IAppDataWrapperModel?, error: Throwable?) -> Unit?) {

        val getAppDataNetworkRequest = GetAppDataRequest(
                environment = environment,
                appId = appId,
                userId = userId,
                token = token
        )

        val future = networkTask.execute(input = getAppDataNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = AppDataWrapper::class.java)
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

    override fun hasTriggeredEvent(eventId: Int, userId: Int, token: String, callback: (hasTriggeredEvent: IHasTriggeredEventModel?, error: Throwable?) -> Unit) {

        val hasTriggeredEventNetworkRequest = HasTriggeredEventRequest(
                environment = environment,
                userId = userId,
                eventId = eventId,
                token = token
        )

        val future = networkTask.execute(input = hasTriggeredEventNetworkRequest)

        future.onResult { networkResult ->

            val parse = ParseJsonTask(type = HasTriggeredEvent::class.java)
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

    override fun inviteUser(email: String, userId: Int, token: String, callback: (error: Throwable?) -> Unit) {

        val inviteUserNetworkRequest = InviteUserRequest(
                environment = environment,
                userId = userId,
                token = token,
                emailAddress = email
        )

        handleNetworkResponse(request = inviteUserNetworkRequest, callback = callback)
    }

    override fun requestPermissions(permissions: List<String>, userId: Int, token: String, callback: (error: Throwable?) -> Unit) {
        val requestPermissionsNetworkRequest = PermissionsRequest(
                environment = environment,
                userId = userId,
                token = token,
                permissionsList = permissions
        )

        handleNetworkResponse(request = requestPermissionsNetworkRequest, callback = callback)
    }

    override fun setAppData(value: Int, key: String, userId: Int, appId: Int, token: String, callback: (error: Throwable?) -> Unit) {

        val setAppDataNetworkRequest = SetAppDataRequest(
                environment = environment,
                appId = appId,
                userId = userId,
                value = value,
                key = key,
                token = token
        )

        handleNetworkResponse(request = setAppDataNetworkRequest, callback = callback)
    }

    override fun triggerEvent(eventId: String, points: Int, userId: Int, token: String, callback: (error: Throwable?) -> Unit) {
        val triggerEventNetworkRequest = TriggerEventRequest(
                environment = environment,
                eventId = eventId,
                points = points,
                userId = userId,
                token = token
        )

        handleNetworkResponse(request = triggerEventNetworkRequest, callback = callback)
    }


    private fun handleNetworkResponse(request: INetworkRequest,
                                      callback: (error: Throwable?) -> Unit) {

        val future = networkTask.execute(input = request)
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
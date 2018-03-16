package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AppDataWrapper
import kws.superawesome.tv.kwssdk.base.models.HasTriggeredEvent
import kws.superawesome.tv.kwssdk.base.models.SDKException
import kws.superawesome.tv.kwssdk.base.requests.*
import org.json.JSONException
import tv.superawesome.protobufs.features.user.IUserActionsService
import tv.superawesome.protobufs.models.appdata.IAppDataWrapperModel
import tv.superawesome.protobufs.models.score.IHasTriggeredEventModel
import tv.superawesome.samobilebase.network.NetworkRequest
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
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

        networkTask.execute(input = getAppDataNetworkRequest) { payload ->

            if (payload.success && payload.response != null) {

                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val parseTask = ParseJsonTask()
                val result = parseTask.execute<AppDataWrapper>(input = parseRequest,
                        clazz = AppDataWrapper::class.java)

                //
                // send callback
                val error = if (result != null) null else JSONException(AppDataWrapper::class.java.toString())
                callback(result, error)


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

    override fun hasTriggeredEvent(eventId: Int, userId: Int, token: String, callback: (hasTriggeredEvent: IHasTriggeredEventModel?, error: Throwable?) -> Unit) {

        val hasTriggeredEventNetworkRequest = HasTriggeredEventRequest(
                environment = environment,
                userId = userId,
                eventId = eventId,
                token = token
        )

        networkTask.execute(input = hasTriggeredEventNetworkRequest) { payload ->

            if (payload.success && payload.response != null) {


                val parseRequest = ParseJsonRequest(rawString = payload.response)
                val parseTask = ParseJsonTask()
                val result = parseTask.execute<HasTriggeredEvent>(input = parseRequest, clazz = HasTriggeredEvent::class.java)

                //
                //send callback
                val error = if (result != null) null else JSONException(HasTriggeredEvent::class.java.toString())
                callback(result, error)

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
                points = points,
                userId = userId,
                token = token,
                eventToken = token
        )

        handleNetworkResponse(request = triggerEventNetworkRequest, callback = callback)
    }


    private fun handleNetworkResponse(request: NetworkRequest,
                                      callback: (error: Throwable?) -> Unit) {

        networkTask.execute(input = request) { payload ->

            val serverError = super.parseServerError(serverError = payload.error)

            // network success cae
            if (payload.status == 200 && serverError == null) {
                callback(null)
            }
            // server error case
            else if (serverError != null) {
                callback(serverError)
            }
            // unknown error case
            else {
                val error = SDKException()
                callback(error)
            }
        }
    }

}
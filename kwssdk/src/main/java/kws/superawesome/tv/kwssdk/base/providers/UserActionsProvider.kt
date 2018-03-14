package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AppDataWrapper
import kws.superawesome.tv.kwssdk.base.models.HasTriggeredEvent
import kws.superawesome.tv.kwssdk.base.requests.*
import tv.superawesome.protobufs.features.user.IUserActionsService
import tv.superawesome.protobufs.models.appdata.IAppDataWrapperModel
import tv.superawesome.protobufs.models.score.IHasTriggeredEventModel
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 05/01/2018.
 */
@PublishedApi
internal class UserActionsProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : IUserActionsService {


    override fun getAppData(userId: Int, appId: Int, token: String, callback: (appData: IAppDataWrapperModel?, error: Throwable?) -> Unit?) {

        val getAppDataNetworkRequest = GetAppDataRequest(
                environment = environment,
                appId = appId,
                userId = userId,
                token = token
        )

        networkTask.execute(input = getAppDataNetworkRequest) { getAppDataNetworkResponse ->

            if (getAppDataNetworkResponse.response != null && getAppDataNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = getAppDataNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getAppDataResponseObject = parseTask.execute<AppDataWrapper>(input = parseRequest,
                        clazz = AppDataWrapper::class.java)

                //
                // send callback
                val error = if (getAppDataResponseObject == null) Throwable("Error getting app data") else null
                callback(getAppDataResponseObject, error)


            } else {
                //
                //network failure
                callback(null, getAppDataNetworkResponse.error)
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

        networkTask.execute(input = hasTriggeredEventNetworkRequest) { hasTriggeredEventNetworkResponse ->

            if (hasTriggeredEventNetworkResponse.response != null && hasTriggeredEventNetworkResponse.error == null) {


                val parseRequest = ParseJsonRequest(rawString = hasTriggeredEventNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val hasTriggeredEventObject = parseTask.execute<HasTriggeredEvent>(input = parseRequest, clazz = HasTriggeredEvent::class.java)

                //
                //send callback
                val error = if (hasTriggeredEventObject != null) null else Throwable("Error - not valid login")
                callback(hasTriggeredEventObject, error)

            } else {
                //
                //network failure
                callback(null, hasTriggeredEventNetworkResponse.error)
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

        networkTask.execute(input = inviteUserNetworkRequest) { inviteUserNetworkResponse ->


            //TODO


            //
            //send callback
//            callback((inviteUserNetworkResponse.status == 200 || inviteUserNetworkResponse.status == 204)
//                    && inviteUserNetworkResponse.error == null, inviteUserNetworkResponse.error)

        }
    }

    override fun requestPermissions(permissions: List<String>, userId: Int, token: String, callback: (error: Throwable?) -> Unit) {
        val requestPermissionsNetworkRequest = PermissionsRequest(
                environment = environment,
                userId = userId,
                token = token,
                permissionsList = permissions
        )

        networkTask.execute(input = requestPermissionsNetworkRequest) { requestPermissionsNetworkResponse ->

            if (requestPermissionsNetworkResponse.response != null && requestPermissionsNetworkResponse.error == null) {

                //
                //send callback
                if (requestPermissionsNetworkResponse.status == 200
                        || requestPermissionsNetworkResponse.status == 204) {
                    callback(null)
                } else {
                    //
                    // we have a response, but something else went wrong
                    val payload = requestPermissionsNetworkResponse.response
                    val message = if (payload != null) payload else "Unknown network error"
                    val error = Throwable(message)
                    callback( error)
                }

            } else {
                //
                // network failure
                callback(requestPermissionsNetworkResponse.error)
            }


        }
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

        networkTask.execute(input = setAppDataNetworkRequest) { setAppDataNetworkResponse ->


            //TODO CALLBACK
//            callback((setAppDataNetworkResponse.status == 200 || setAppDataNetworkResponse.status == 204)
//                    && setAppDataNetworkResponse.error == null, setAppDataNetworkResponse.error)

        }
    }

    override fun triggerEvent(eventId: String, points: Int, userId: Int, token: String, callback: (error: Throwable?) -> Unit) {
        val triggerEventNetworkRequest = TriggerEventRequest(
                environment = environment,
                points = points,
                userId = userId,
                token = token,
                eventToken = token
        )

        networkTask.execute(input = triggerEventNetworkRequest) { triggerEventNetworkResponse ->

            //TODO trigger event callback
            //
            // send callback
//            callback((triggerEventNetworkResponse.status == 200 || triggerEventNetworkResponse.status == 204)
//                    && triggerEventNetworkResponse.error == null, triggerEventNetworkResponse.error)

        }
    }


}
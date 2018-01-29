package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.HasTriggeredEventRequest
import kws.superawesome.tv.kwssdk.base.requests.TriggerEventRequest
import kws.superawesome.tv.kwssdk.base.responses.HasTriggeredEvent
import kws.superawesome.tv.kwssdk.base.services.EventsService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 04/01/2018.
 */
@PublishedApi
internal class EventsProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : EventsService {


    override fun triggerEvent(points: Int, userId: Int, token: String, eventToken: String,
                              callback: (success: Boolean?, error: Throwable?) -> Unit) {

        val triggerEventNetworkRequest = TriggerEventRequest(
                environment = environment,
                points = points,
                userId = userId,
                token = token,
                eventToken = eventToken
        )

        networkTask.execute(input = triggerEventNetworkRequest) { triggerEventNetworkResponse ->

            //
            // send callback
            callback((triggerEventNetworkResponse.status == 200 || triggerEventNetworkResponse.status == 204)
                    && triggerEventNetworkResponse.error == null, triggerEventNetworkResponse.error)

        }


    }

    override fun hasTriggeredEvent(userId: Int, eventId: Int, token: String,
                                   callback: (hasTriggeredEvent: HasTriggeredEvent?, error: Throwable?) -> Unit) {

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


}
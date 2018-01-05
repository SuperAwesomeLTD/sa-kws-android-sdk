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
internal class EventsProvider(val environment: KWSNetworkEnvironment) : EventsService {


    override fun triggerEvent(points: Int, userId: Int, token: String, eventToken: String,
                              callback: (success: Boolean?, error: Throwable?) -> Unit) {

        val triggerEventRequest = TriggerEventRequest(
                environment = environment,
                points = points,
                userId = userId,
                token = token,
                eventToken = eventToken
        )

        val triggerEventNetworkTask = NetworkTask()
        triggerEventNetworkTask.execute(input = triggerEventRequest) { rawString, networkError ->

            //
            // send callback
            callback(rawString.isNullOrEmpty() && networkError == null, networkError)

        }


    }

    override fun hasTriggeredEvent(userId: Int, eventId: Int, token: String,
                                   callback: (hasTriggeredEvent: HasTriggeredEvent?, error: Throwable?) -> Unit) {

        val hasTriggeredEventRequest = HasTriggeredEventRequest(
                environment = environment,
                userId = userId,
                eventId = eventId,
                token = token
        )

        val hasTriggeredEventNetworkTask = NetworkTask()
        hasTriggeredEventNetworkTask.execute(input = hasTriggeredEventRequest) { rawString, networkError ->

            if (rawString != null && networkError == null) {


                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val hasTriggeredEvent = parseTask.execute<HasTriggeredEvent>(input = parseRequest, clazz = HasTriggeredEvent::class.java)

                //
                //send callback
                val error = if (hasTriggeredEvent != null) null else Throwable("Error - not valid login")
                callback(hasTriggeredEvent, error)

            } else {
                //
                //network failure
                callback(null, networkError)
            }

        }


    }


}
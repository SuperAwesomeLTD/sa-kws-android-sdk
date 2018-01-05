package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.HasTriggeredEventRequest
import kws.superawesome.tv.kwssdk.base.responses.HasTriggeredEvent
import kws.superawesome.tv.kwssdk.base.responses.Login
import kws.superawesome.tv.kwssdk.base.services.HasTriggeredEventService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 05/01/2018.
 */
@PublishedApi
internal class HasTriggeredEventProvider(val environment: KWSNetworkEnvironment) : HasTriggeredEventService {


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
                callback(hasTriggeredEvent,error)

            } else {
                callback(null, networkError)
            }

        }


    }


}
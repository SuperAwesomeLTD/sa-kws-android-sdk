package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.HasTriggeredEventRequest
import kws.superawesome.tv.kwssdk.base.services.HasTriggeredEventService
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 05/01/2018.
 */
@PublishedApi
internal class HasTriggeredEventProvider(val environment: KWSNetworkEnvironment) : HasTriggeredEventService {


    override fun hasTriggeredEvent(userId: Int, eventId: Int, token: String,
                                   callback: (success: Boolean, error: Throwable?) -> Unit) {

        val hasTriggeredEventRequest = HasTriggeredEventRequest(
                environment = environment,
                userId = userId,
                eventId = eventId,
                token = token
        )

        val hasTriggeredEventNetworkTask = NetworkTask()
        hasTriggeredEventNetworkTask.execute(input = hasTriggeredEventRequest) { rawString, networkError ->

            if (rawString.isNullOrEmpty() && networkError == null) {
                //
                // send callback
                callback(true, null)
            } else {
                callback(false, networkError)
            }

        }


    }


}
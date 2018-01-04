package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.TriggerEventRequest
import kws.superawesome.tv.kwssdk.base.services.TriggerEventService
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 04/01/2018.
 */
@PublishedApi
internal class TriggerEventProvider(val environment: KWSNetworkEnvironment) : TriggerEventService {


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
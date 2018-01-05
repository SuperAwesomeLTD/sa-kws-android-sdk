package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 05/01/2018.
 */
internal class HasTriggeredEventRequest (environment: KWSNetworkEnvironment,
                                         userId: Int,
                                         eventId: Int,
                                         token: String

): BaseRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/users/$userId/has-didTriggerEvent-event"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "eventId" to eventId
    )

}
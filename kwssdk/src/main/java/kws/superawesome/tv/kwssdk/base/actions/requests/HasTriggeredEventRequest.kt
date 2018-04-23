package kws.superawesome.tv.kwssdk.base.actions.requests

import kws.superawesome.tv.kwssdk.base.common.requests.AbstractRequest
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 05/01/2018.
 */
/*internal*/
class HasTriggeredEventRequest(environment: NetworkEnvironment,
                               userId: Int,
                               eventId: Int,
                               token: String

) : AbstractRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/users/$userId/has-triggered-event"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf("eventId" to eventId)

}
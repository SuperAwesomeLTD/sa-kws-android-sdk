package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 04/01/2018.
 */
/*internal*/
class TriggerEventRequest(environment: KWSNetworkEnvironment,
                          eventId: String,
                          points: Int,
                          userId: Int,
                          token: String

) : BaseRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/users/$userId/trigger-event"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "points" to points,
            "token" to eventId)

}
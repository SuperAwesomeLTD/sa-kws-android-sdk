package kws.superawesome.tv.kwssdk.base.common

import tv.superawesome.samobilebase.network.*


/**
 * Created by guilherme.mota on 08/12/2017.
 */
/*internal*/
abstract class AbstractRequest(override val environment: INetworkEnvironment,
                               val token: String? = null) : INetworkRequest {

    override val headers: Map<String, String>?
        get() {
            val head = mutableMapOf("Content-Type" to "application/json")
            token?.let {
                head.put("Authorization", "Bearer $it")
            }
            return head
        }

    override val query: Map<String, Any>? = null

    override val method: NetworkMethod = NetworkMethod.GET

    override val body: Map<String, Any>? = null
}
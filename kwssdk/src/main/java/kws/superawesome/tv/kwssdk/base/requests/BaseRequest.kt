package kws.superawesome.tv.kwssdk.base.requests

import tv.superawesome.samobilebase.network.NetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod
import tv.superawesome.samobilebase.network.NetworkRequest


/**
 * Created by guilherme.mota on 08/12/2017.
 */
internal abstract class BaseRequest(override val environment: NetworkEnvironment,
                                    val token: String? = null) : NetworkRequest {

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
package kws.superawesome.tv.kwssdk.base.requests

import tv.superawesome.samobilebase.network.NetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod
import tv.superawesome.samobilebase.network.NetworkRequest


/**
 * Created by guilherme.mota on 08/12/2017.
 */
internal abstract class BaseRequest(override val environment: NetworkEnvironment,
                                    token: String? = null) : NetworkRequest {

    val defaultHeader: Map<String, String> = mapOf(
            "Accept-Language" to "en",
            "X-Client-Version" to "ios-1.0.0",
            "Content-Type" to "application/json")

    val authHeader: Map<String, String>? =
            token?.let {
                mapOf("Authorization" to "Bearer $it")
            }

    override val headers: Map<String, String> = defaultHeader + (authHeader ?: mapOf())

    override val query: Map<String, Any>? = null

    override val method: NetworkMethod = NetworkMethod.GET

    override val body: Map<String, Any>? = null
}
package kws.superawesome.tv.kwssdk.authentication.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 12/12/2017.
 */
/*internal*/
class TempAccessTokenRequest(environment: NetworkEnvironment,
                             clientID: String,
                             clientSecret: String
) : AbstractRequest(environment = environment) {

    override val headers: Map<String, String> = mapOf("Content-Type" to "application/x-www-form-urlencoded")

    override val endpoint: String = "oauth/token"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "grant_type" to "client_credentials",
            "client_id" to clientID,
            "client_secret" to clientSecret
    )

    override val formEncodeUrls: Boolean
        get() = true
}
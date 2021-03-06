package kws.superawesome.tv.kwssdk.authentication.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 24/01/2018.
 */
class OAuthUserTokenRequest(environment: NetworkEnvironment,
                            clientID: String,
                            authCode: String,
                            codeVerifier: String,
                            clientSecret: String
) : AbstractRequest(environment = environment) {

    override val headers: Map<String, String> = mapOf("Content-Type" to "application/x-www-form-urlencoded")

    override val endpoint: String = "oauth/token"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientID,
            "code" to authCode,
            "client_secret" to clientSecret,
            "code_verifier" to codeVerifier
    )

    override val formEncodeUrls: Boolean
        get() = true
}
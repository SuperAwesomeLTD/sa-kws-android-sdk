package kws.superawesome.tv.kwssdk.base.authentication.requests

import kws.superawesome.tv.kwssdk.base.BaseRequest
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 24/01/2018.
 */
class OAuthUserTokenRequest(environment: KWSNetworkEnvironment,
                            clientID: String,
                            authCode: String,
                            codeVerifier: String,
                            clientSecret: String
) : BaseRequest(environment = environment) {

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
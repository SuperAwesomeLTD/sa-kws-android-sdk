package kws.superawesome.tv.kwssdk.base.authentication.requests

import kws.superawesome.tv.kwssdk.base.BaseRequest
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 08/12/2017.
 */
/*internal*/
class LoginUserRequest(environment: KWSNetworkEnvironment,
                       username: String,
                       password: String,
                       clientID: String,
                       clientSecret: String
) : BaseRequest(environment = environment) {

    override val headers: Map<String, String> = mapOf("Content-Type" to "application/x-www-form-urlencoded")

    override val endpoint: String = "oauth/token"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "grant_type" to "password",
            "username" to username,
            "password" to password,
            "client_id" to clientID,
            "client_secret" to clientSecret
    )

    override val formEncodeUrls: Boolean
        get() = true

}
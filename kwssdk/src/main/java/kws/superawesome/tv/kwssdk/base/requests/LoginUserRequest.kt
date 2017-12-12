package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.androidbaselib.network.NetworkEnvironment
import kws.superawesome.tv.androidbaselib.network.NetworkMethod

/**
 * Created by guilherme.mota on 08/12/2017.
 */
internal class LoginUserRequest(environment: NetworkEnvironment,
                                username: String,
                                password: String,
                                clientID: String,
                                clientSecret: String
) :
        BaseRequest(environment = environment) {
    override val mediaType: String
        get() = "application/x-www-form-urlencoded; charset=utf-8"
    override val isURLEncoded: Boolean
        get() = true

    override val headers: Map<String, String>
        get() {
            val map = mutableMapOf<String, String>()
            map.set("Content-Type", "application/x-www-form-urlencoded")
            return map
        }

    override val endpoint: String = "oauth/token"
    override val method: NetworkMethod = NetworkMethod.POST
    override val body: Map<String, Any>? = mapOf(
            "grant_type" to "password",
            "username" to username,
            "password" to password,
            "client_id" to clientID,
            "client_secret" to clientSecret
    )

}
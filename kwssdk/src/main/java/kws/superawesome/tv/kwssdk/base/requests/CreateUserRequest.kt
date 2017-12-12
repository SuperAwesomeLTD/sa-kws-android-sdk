package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.androidbaselib.network.NetworkMethod
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment

/**
 * Created by guilherme.mota on 12/12/2017.
 */
internal class CreateUserRequest(environment: KWSNetworkEnvironment,
                                 username: String,
                                 password: String,
                                 dateOfBirth: String,
                                 country: String,
                                 parentEmail: String,
                                 appID: Int,
                                 token: String)
    : BaseRequest(environment = environment) {

    override val mediaType: String
        get() = "application/json; charset=utf-8"
    override val isURLEncoded: Boolean
        get() = true

    override val headers: Map<String, String>
        get() {
            val map = mutableMapOf<String, String>()
            map.set("Content-Type", "application/json")
            return map
        }

    override val endpoint: String = "v1/apps/$appID/users?access_token=$token"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "username" to username,
            "password" to password,
            "dateOfBirth" to dateOfBirth,
            "country" to country,
            "parentEmail" to parentEmail,
            "authenticate" to true
    )

}
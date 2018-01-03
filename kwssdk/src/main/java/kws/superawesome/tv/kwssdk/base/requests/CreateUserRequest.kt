package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

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

    override val headers: Map<String, String> = mapOf("Content-Type" to "application/json")

    override val endpoint: String = "v1/apps/$appID/users"

    override val query: Map<String, Any>? = mapOf("access_token" to token)

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
package kws.superawesome.tv.kwssdk.authentication.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 12/12/2017.
 */
/*internal*/
class CreateUserRequest(environment: NetworkEnvironment,
                        username: String,
                        password: String,
                        dateOfBirth: String,
                        country: String,
                        parentEmail: String,
                        appID: Int,
                        token: String)
    : AbstractRequest(environment = environment) {

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
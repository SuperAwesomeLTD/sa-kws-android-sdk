package kws.superawesome.tv.kwssdk.actions.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 04/01/2018.
 */
/*internal*/
class InviteUserRequest(environment: NetworkEnvironment,
                        emailAddress: String,
                        userId: Int,
                        token: String
) : AbstractRequest(environment = environment, token = token) {

    override val endpoint: String = "v1/users/$userId/invite-user"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf("email" to emailAddress)

}

package kws.superawesome.tv.kwssdk.base.invite

import kws.superawesome.tv.kwssdk.base.common.requests.AbstractRequest
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
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

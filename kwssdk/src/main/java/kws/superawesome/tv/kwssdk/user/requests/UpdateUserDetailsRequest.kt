package kws.superawesome.tv.kwssdk.user.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest
import tv.superawesome.samobilebase.network.NetworkMethod

class UpdateUserDetailsRequest(environment: NetworkEnvironment,
                               userDetailsMap: Map<String, Any>,
                               userId: Int,
                               token: String
) : AbstractRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/users/$userId"

    override val method: NetworkMethod = NetworkMethod.PUT

    override val body: Map<String, Any>? = userDetailsMap

}
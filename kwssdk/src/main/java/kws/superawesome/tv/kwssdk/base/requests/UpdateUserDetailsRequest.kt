package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

class UpdateUserDetailsRequest (environment: KWSNetworkEnvironment,
                                userDetailsMap: Map<String, Any>,
                                userId: Int,
                                token: String
) : BaseRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/users/$userId"

    override val method: NetworkMethod = NetworkMethod.PUT

    override val body: Map<String, Any>? = userDetailsMap

}
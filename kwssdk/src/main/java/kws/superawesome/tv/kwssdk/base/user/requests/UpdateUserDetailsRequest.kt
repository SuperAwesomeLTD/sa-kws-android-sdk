package kws.superawesome.tv.kwssdk.base.user.requests

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.BaseRequest
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
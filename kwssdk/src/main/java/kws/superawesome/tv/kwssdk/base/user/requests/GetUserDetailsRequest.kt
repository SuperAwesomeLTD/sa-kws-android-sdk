package kws.superawesome.tv.kwssdk.base.user.requests

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.BaseRequest

/**
 * Created by guilherme.mota on 03/01/2018.
 */
/*internal*/
class GetUserDetailsRequest(environment: KWSNetworkEnvironment,
                            userId: Int,
                            token: String
) : BaseRequest(environment = environment, token = token) {

    override val endpoint: String = "v1/users/$userId"


}
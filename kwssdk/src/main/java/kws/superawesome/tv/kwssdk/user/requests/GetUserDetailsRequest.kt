package kws.superawesome.tv.kwssdk.base.user.requests

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.common.requests.AbstractRequest

/**
 * Created by guilherme.mota on 03/01/2018.
 */
/*internal*/
class GetUserDetailsRequest(environment: NetworkEnvironment,
                            userId: Int,
                            token: String
) : AbstractRequest(environment = environment, token = token) {

    override val endpoint: String = "v1/users/$userId"


}
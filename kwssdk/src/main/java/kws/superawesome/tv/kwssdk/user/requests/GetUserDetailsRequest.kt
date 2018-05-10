package kws.superawesome.tv.kwssdk.user.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest

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
package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment

/**
 * Created by guilherme.mota on 03/01/2018.
 */
/*internal*/
class UserDetailsRequest(environment: KWSNetworkEnvironment,
                         userId: Int,
                         token: String
) : BaseRequest(environment = environment, token = token) {

    override val endpoint: String = "v1/users/$userId"


}
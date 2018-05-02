package kws.superawesome.tv.kwssdk.base.scoring.requests

import kws.superawesome.tv.kwssdk.base.common.requests.AbstractRequest
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment

/**
 * Created by guilherme.mota on 05/01/2018.
 */
/*internal*/
class LeadersRequest(environment: NetworkEnvironment,
                     appId: Int,
                     token: String
) : AbstractRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/apps/$appId/leaders"


}
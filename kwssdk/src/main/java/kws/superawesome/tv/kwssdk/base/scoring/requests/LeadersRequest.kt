package kws.superawesome.tv.kwssdk.base.scoring.requests

import kws.superawesome.tv.kwssdk.base.BaseRequest
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment

/**
 * Created by guilherme.mota on 05/01/2018.
 */
/*internal*/
class LeadersRequest(environment: KWSNetworkEnvironment,
                     appId: Int,
                     token: String
) : BaseRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/apps/$appId/leaders"


}
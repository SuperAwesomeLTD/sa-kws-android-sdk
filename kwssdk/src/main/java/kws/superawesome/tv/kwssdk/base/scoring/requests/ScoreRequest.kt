package kws.superawesome.tv.kwssdk.base.scoring.requests

import kws.superawesome.tv.kwssdk.base.common.AbstractRequest
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment

/**
 * Created by guilherme.mota on 08/01/2018.
 */
/*internal*/
class ScoreRequest(environment: NetworkEnvironment,
                   appId: Int,
                   token: String
) : AbstractRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/apps/$appId/score"

}


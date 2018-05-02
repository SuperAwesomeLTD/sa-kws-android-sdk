package kws.superawesome.tv.kwssdk.base.username.requests

import kws.superawesome.tv.kwssdk.base.common.requests.AbstractRequest
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment

/**
 * Created by guilherme.mota on 29/12/2017.
 */
/*internal*/
class RandomUsernameRequest(environment: NetworkEnvironment, appID: Int)
    : AbstractRequest(environment = environment) {

    override val endpoint: String = "v2/apps/$appID/random-display-name"

}
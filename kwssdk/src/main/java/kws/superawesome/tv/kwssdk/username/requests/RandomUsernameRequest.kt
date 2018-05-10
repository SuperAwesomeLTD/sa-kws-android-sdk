package kws.superawesome.tv.kwssdk.username.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest

/**
 * Created by guilherme.mota on 29/12/2017.
 */
/*internal*/
class RandomUsernameRequest(environment: NetworkEnvironment, appID: Int)
    : AbstractRequest(environment = environment) {

    override val endpoint: String = "v2/apps/$appID/random-display-name"

}
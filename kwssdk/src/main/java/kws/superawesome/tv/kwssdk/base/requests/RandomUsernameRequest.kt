package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment

/**
 * Created by guilherme.mota on 29/12/2017.
 */
/*internal*/
class RandomUsernameRequest(environment: KWSNetworkEnvironment, appID: Int)
    : BaseRequest(environment = environment) {

    override val endpoint: String = "v2/apps/$appID/random-display-name"

}
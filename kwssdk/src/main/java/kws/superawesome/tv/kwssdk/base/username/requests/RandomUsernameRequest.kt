package kws.superawesome.tv.kwssdk.base.username.requests

import kws.superawesome.tv.kwssdk.base.BaseRequest
import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment

/**
 * Created by guilherme.mota on 29/12/2017.
 */
/*internal*/
class RandomUsernameRequest(environment: KWSNetworkEnvironment, appID: Int)
    : BaseRequest(environment = environment) {

    override val endpoint: String = "v2/apps/$appID/random-display-name"

}
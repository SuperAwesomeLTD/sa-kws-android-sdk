package kws.superawesome.tv.kwssdk.config.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest

/**
 * Created by guilherme.mota on 29/12/2017.
 */
/*internal*/
class AppConfigRequest(environment: NetworkEnvironment, clientID: String)
    : AbstractRequest(environment = environment) {

    override val endpoint: String = "v1/apps/config"

    override val query: Map<String, Any>? = mapOf("oauthClientId" to clientID)


}
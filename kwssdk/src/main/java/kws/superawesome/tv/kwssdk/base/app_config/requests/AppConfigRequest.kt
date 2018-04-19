package kws.superawesome.tv.kwssdk.base.app_config.requests

import kws.superawesome.tv.kwssdk.base.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.BaseRequest

/**
 * Created by guilherme.mota on 29/12/2017.
 */
/*internal*/
class AppConfigRequest(environment: KWSNetworkEnvironment, clientID: String)
    : BaseRequest(environment = environment) {

    override val endpoint: String = "v1/apps/config"

    override val query: Map<String, Any>? = mapOf("oauthClientId" to clientID)


}
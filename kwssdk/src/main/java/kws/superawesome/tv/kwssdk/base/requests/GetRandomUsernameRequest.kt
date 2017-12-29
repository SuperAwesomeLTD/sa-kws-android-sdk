package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 29/12/2017.
 */
internal class GetRandomUsernameRequest(environment: KWSNetworkEnvironment, appID: Int)
    : BaseRequest(environment = environment) {

    override val headers: Map<String, String>
        get() {
            val map = mutableMapOf<String, String>()
            map.set("Content-Type", "application/json")
            return map
        }

    override val endpoint: String = "v2/apps/$appID/random-display-name"

    override val method: NetworkMethod = NetworkMethod.GET

    override val body: Map<String, Any>? = null


}
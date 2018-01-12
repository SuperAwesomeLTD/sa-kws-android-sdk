package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 08/01/2018.
 */
/*internal*/
class SetAppDataRequest(environment: KWSNetworkEnvironment,
                        appId: Int,
                        userId: Int,
                        nameValue: String,
                        numericValue: Int,
                        token: String
) : BaseRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/apps/$appId/users/$userId/app-data/set"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "name" to nameValue,
            "value" to numericValue
    )


}
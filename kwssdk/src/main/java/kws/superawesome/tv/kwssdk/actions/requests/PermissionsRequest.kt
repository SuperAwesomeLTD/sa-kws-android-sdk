package kws.superawesome.tv.kwssdk.base.actions.requests

import kws.superawesome.tv.kwssdk.base.common.requests.AbstractRequest
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import org.json.JSONArray
import tv.superawesome.samobilebase.network.NetworkMethod

/**
 * Created by guilherme.mota on 09/01/2018.
 */
/*internal*/
class PermissionsRequest(environment: NetworkEnvironment,
                         userId: Int,
                         token: String,
                         permissionsList: List<String>

) : AbstractRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/users/$userId/request-permissions"

    override val method: NetworkMethod = NetworkMethod.POST

    override val body: Map<String, Any>? = mapOf(
            "permissions" to JSONArray(permissionsList)
    )


}
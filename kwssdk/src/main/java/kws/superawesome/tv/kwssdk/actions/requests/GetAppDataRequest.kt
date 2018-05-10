package kws.superawesome.tv.kwssdk.actions.requests

import kws.superawesome.tv.kwssdk.NetworkEnvironment
import kws.superawesome.tv.kwssdk.common.requests.AbstractRequest

/**
 * Created by guilherme.mota on 08/01/2018.
 */
/*internal*/
class GetAppDataRequest(environment: NetworkEnvironment,
                        appId: Int,
                        userId: Int,
                        token: String
) : AbstractRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/apps/$appId/users/$userId/app-data"


}
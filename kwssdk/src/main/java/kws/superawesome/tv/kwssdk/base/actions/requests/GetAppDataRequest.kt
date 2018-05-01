package kws.superawesome.tv.kwssdk.base.actions.requests

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.common.requests.AbstractRequest

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
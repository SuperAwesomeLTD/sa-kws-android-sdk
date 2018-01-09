package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment

/**
 * Created by guilherme.mota on 08/01/2018.
 */
internal class GetAppDataRequest(environment: KWSNetworkEnvironment,
                        appId: Int,
                        userId: Int,
                        token: String
) : BaseRequest(environment = environment, token = token) {


    override val endpoint: String = "v1/apps/$appId/users/$userId/app-data"



}
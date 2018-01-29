package kws.superawesome.tv.kwssdk.base.environments

import tv.superawesome.samobilebase.network.NetworkEnvironment

/**
 * Created by guilherme.mota on 12/12/2017.
 */
interface KWSNetworkEnvironment: NetworkEnvironment {
    val appID: String
    val mobileKey: String
}
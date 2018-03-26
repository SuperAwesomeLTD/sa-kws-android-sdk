package kws.superawesome.tv.kwssdk.base.environments

import tv.superawesome.samobilebase.network.INetworkEnvironment

/**
 * Created by guilherme.mota on 12/12/2017.
 */
interface KWSNetworkEnvironment : INetworkEnvironment {
    val appID: String
    val mobileKey: String
}
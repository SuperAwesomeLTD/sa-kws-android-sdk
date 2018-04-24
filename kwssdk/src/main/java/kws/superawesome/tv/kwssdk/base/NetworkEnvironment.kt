package kws.superawesome.tv.kwssdk.base

import tv.superawesome.samobilebase.network.INetworkEnvironment

/**
 * Created by guilherme.mota on 12/12/2017.
 */
interface NetworkEnvironment : INetworkEnvironment {
    val clientID: String
    val clientSecret: String
}
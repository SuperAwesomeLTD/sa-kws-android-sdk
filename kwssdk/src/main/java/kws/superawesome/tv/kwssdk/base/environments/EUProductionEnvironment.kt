package kws.superawesome.tv.kwssdk.base.environments

import kws.superawesome.tv.androidbaselib.network.NetworkEnvironment

/**
 * Created by guilherme.mota on 11/12/2017.
 */
class EUProductionEnvironment : NetworkEnvironment {
    override val appID: String = "stan-test"
    override val mobileKey: String = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4"
    override val domain: String = "https://stan-test-cluster.api.kws.superawesome.tv/"
}
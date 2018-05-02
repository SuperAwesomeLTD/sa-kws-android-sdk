package kws.superawesome.tv.kwsdemo.environments

import kws.superawesome.tv.kwssdk.base.NetworkEnvironment

data class StanTestEnvironments(override val domain: String = "https://stan-test-cluster.api.kws.superawesome.tv/",
                                override val clientID: String = "stan-test",
                                override val clientSecret: String = "DRYNvSStuSvnaDg0d3f9t17QybbpQqX4",

                                val singleSignOn: String = "https://stan-test-cluster.accounts.kws.superawesome.tv/") : NetworkEnvironment
package kws.superawesome.tv.kwsdemo.environments

import kws.superawesome.tv.kwssdk.NetworkEnvironment

data class DemoEnvironments(override val domain: String = "https://kwsapi.demo.superawesome.tv/",
                            override val clientID: String = "kws-sdk-testing",
                            override val clientSecret: String = "TKZpmBq3wWjSuYHN27Id0hjzN4cIL13D",

                            val singleSignOn: String = "https://club.demo.superawesome.tv/") : NetworkEnvironment

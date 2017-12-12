package kws.superawesome.tv.androidbaselib.network

import kws.superawesome.tv.androidbaselib.network.NetworkEnvironment
import kws.superawesome.tv.androidbaselib.network.NetworkMethod

interface NetworkRequest {
    val environment: NetworkEnvironment
    val method: NetworkMethod
    val endpoint: String
    val parameters: Map<String, Any>?
    val body: Map<String, Any>?
    val headers: Map<String, String>
}
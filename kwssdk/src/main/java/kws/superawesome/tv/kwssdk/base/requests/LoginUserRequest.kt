package kws.superawesome.tv.kwssdk.base.requests

import kws.superawesome.tv.androidbaselib.network.NetworkEnvironment
import kws.superawesome.tv.androidbaselib.network.NetworkMethod
import kws.superawesome.tv.kwssdk.base.services.BaseService

/**
 * Created by guilherme.mota on 08/12/2017.
 */
internal class LoginUserRequest (environment: NetworkEnvironment,
                                 username: String,
                                 password: String):
        BaseRequest(environment = environment)
{
    override val endpoint: String = "/users/login"
    override val method: NetworkMethod = NetworkMethod.POST
    override val body: Map<String, Any>? = mapOf(
            "username" to username,
            "password" to password)
}
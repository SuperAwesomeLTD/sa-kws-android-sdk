package kws.superawesome.tv.kwssdk.base

import kws.superawesome.tv.androidbaselib.network.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.providers.LoginProvider
import kws.superawesome.tv.kwssdk.base.services.BaseService
import kws.superawesome.tv.kwssdk.base.services.LoginService

/**
 * Created by guilherme.mota on 11/12/2017.
 */
object KWSSDK {

    @JvmStatic
    inline fun <reified T : BaseService> getService(environment: NetworkEnvironment): T? =
            when (T::class) {
                LoginService::class -> LoginProvider(environment = environment) as T?
                else -> null
            }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T : BaseService> get(environment: NetworkEnvironment, clazz: Class<T>): T? {

        return if (clazz == LoginService::class.java)
            LoginProvider(environment = environment) as T?
        else null
    }

}
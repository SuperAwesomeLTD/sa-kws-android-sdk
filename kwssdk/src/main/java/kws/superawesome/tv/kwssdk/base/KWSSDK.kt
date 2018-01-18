package kws.superawesome.tv.kwssdk.base

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.providers.*
import kws.superawesome.tv.kwssdk.base.services.*
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 11/12/2017.
 */
object KWSSDK {

    @JvmStatic
    inline fun <reified T : BaseService> getService(environment: KWSNetworkEnvironment, networkTask: NetworkTask = NetworkTask()): T? =
            when (T::class) {
                LoginService::class -> LoginProvider(environment = environment, networkTask = networkTask) as T?
                CreateUserService::class -> CreateUserProvider(environment = environment, networkTask = networkTask) as T?
                RandomUsernameService::class -> RandomUsernameProvider(environment = environment, networkTask = networkTask) as T?
                UserService::class -> UserProvider(environment = environment, networkTask = networkTask) as T?
                EventsService::class -> EventsProvider(environment = environment, networkTask = networkTask) as T?
                AppService::class -> AppProvider(environment = environment, networkTask = networkTask) as T?
                else -> null
            }


    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @JvmOverloads
    fun <T : BaseService> get(environment: KWSNetworkEnvironment,
                              clazz: Class<T>,
                              networkTask: NetworkTask = NetworkTask()): T? =
            when (clazz) {
                LoginService::class.java -> LoginProvider(environment = environment, networkTask = networkTask) as T?
                CreateUserService::class.java -> CreateUserProvider(environment = environment, networkTask = networkTask) as T?
                RandomUsernameService::class.java -> RandomUsernameProvider(environment = environment, networkTask = networkTask) as T?
                UserService::class.java -> UserProvider(environment = environment, networkTask = networkTask) as T?
                EventsService::class.java -> EventsProvider(environment = environment, networkTask = networkTask) as T?
                AppService::class.java -> AppProvider(environment = environment, networkTask = networkTask) as T?
                else -> null
            } as T?

}

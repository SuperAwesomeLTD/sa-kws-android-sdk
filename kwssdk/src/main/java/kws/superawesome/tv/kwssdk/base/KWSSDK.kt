package kws.superawesome.tv.kwssdk.base

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.providers.*
import kws.superawesome.tv.kwssdk.base.services.*

/**
 * Created by guilherme.mota on 11/12/2017.
 */
object KWSSDK {

    @JvmStatic
    inline fun <reified T : BaseService> getService(environment: KWSNetworkEnvironment): T? =
            when (T::class) {
                LoginService::class -> LoginProvider(environment = environment) as T?
                CreateUserService::class -> CreateUserProvider(environment = environment) as T?
                GetRandomUsernameService::class -> GetRandomUsernameProvider(environment = environment) as T?
                GetUserDetailsService::class -> GetUserDetailsProvider(environment = environment) as T?
                InviteUserService::class -> InviteUserProvider(environment = environment) as T?
                TriggerEventService::class -> TriggerEventProvider(environment = environment) as T?
                HasTriggeredEventService::class -> HasTriggeredEventProvider(environment = environment) as T?
                else -> null
            }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T : BaseService> get(environment: KWSNetworkEnvironment, clazz: Class<T>): T? {

        return if (clazz == LoginService::class.java)
            LoginProvider(environment = environment) as T?
        else if (clazz == CreateUserService::class.java)
            CreateUserProvider(environment = environment) as T?
        else if (clazz == GetRandomUsernameService::class.java)
            GetRandomUsernameProvider(environment = environment) as T?
        else if (clazz == GetUserDetailsService::class.java)
            GetUserDetailsProvider(environment = environment) as T?
        else if (clazz == InviteUserService::class.java)
            InviteUserProvider(environment = environment) as T?
        else if (clazz == TriggerEventService::class.java)
            TriggerEventProvider(environment = environment) as T?
        else if (clazz == HasTriggeredEventService::class.java)
            HasTriggeredEventProvider(environment = environment) as T?
        else null
    }

}
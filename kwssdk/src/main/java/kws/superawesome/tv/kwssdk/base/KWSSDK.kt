package kws.superawesome.tv.kwssdk.base

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.providers.*
import tv.superawesome.protobufs.IAbstractFactory
import tv.superawesome.protobufs.IService
import tv.superawesome.protobufs.features.auth.IAuthService
import tv.superawesome.protobufs.features.auth.ISingleSignOnService
import tv.superawesome.protobufs.features.auth.IUsernameService
import tv.superawesome.protobufs.features.config.IConfigService
import tv.superawesome.protobufs.features.scoring.IScoringService
import tv.superawesome.protobufs.features.session.ISessionService
import tv.superawesome.protobufs.features.user.IUserActionsService
import tv.superawesome.protobufs.features.user.IUserService
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 11/12/2017.
 */
class KWSSDK
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : IAbstractFactory {


    @Suppress("UNCHECKED_CAST")
    override fun <S : IService> getService(type: Class<S>): S? =
            when (type) {
                IAuthService::class -> AuthProvider(environment = environment, networkTask = networkTask)
                IScoringService::class -> ScoreProvider(environment = environment, networkTask = networkTask)
                IUserActionsService::class -> UserActionsProvider(environment = environment, networkTask = networkTask)
                ISingleSignOnService::class -> SingleSignOnProvider(environment = environment, networkTask = networkTask)
                ISessionService::class -> SessionProvider(environment = environment, networkTask = networkTask)
                IUsernameService::class -> UsernameProvider(environment = environment, networkTask = networkTask)
                IUserService::class -> UserProvider(environment = environment, networkTask = networkTask)
                IConfigService::class -> ConfigProvider(environment = environment, networkTask = networkTask)
                else -> null
            } as S?
}


//    @Suppress("UNCHECKED_CAST")
//    @JvmStatic
//    @JvmOverloads
//    fun <T : BaseService> get(environment: KWSNetworkEnvironment,
//                              clazz: Class<T>,
//                              networkTask: NetworkTask = NetworkTask()): T? =
//            when (clazz) {
//                IAuthService::class.java -> AuthProvider(environment = environment, networkTask = networkTask) as T?
//                IScoringService::class.java -> ScoreProvider(environment = environment, networkTask = networkTask) as T?
//                IUserActionsService::class.java -> UserActionsProvider(environment = environment, networkTask = networkTask) as T?
//                ISingleSignOnService::class.java -> SingleSignOnProvider(environment = environment, networkTask = networkTask) as T?
//                ISessionService::class.java -> SessionProvider(environment = environment, networkTask = networkTask) as T?
//                IUsernameService::class.java -> UsernameProvider(environment = environment, networkTask = networkTask) as T?
//                IUserService::class.java -> UserProvider(environment = environment, networkTask = networkTask) as T?
//                IConfigService::class.java -> ConfigProvider(environment = environment, networkTask = networkTask) as T?
//
//                else -> null
//            } as T?
//}

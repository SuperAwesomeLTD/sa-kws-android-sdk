package kws.superawesome.tv.kwssdk

import kws.superawesome.tv.kwssdk.actions.services.UserActionsService
import kws.superawesome.tv.kwssdk.authentication.services.AuthService
import kws.superawesome.tv.kwssdk.authentication.services.SingleSignOnService
import kws.superawesome.tv.kwssdk.scoring.services.ScoreService
import kws.superawesome.tv.kwssdk.session.services.SessionService
import kws.superawesome.tv.kwssdk.user.services.UserService
import kws.superawesome.tv.kwssdk.username.services.UsernameService
import tv.superawesome.protobufs.actions.services.IUserActionsService
import tv.superawesome.protobufs.authentication.services.IAuthService
import tv.superawesome.protobufs.authentication.services.ISingleSignOnService
import tv.superawesome.protobufs.common.services.IAbstractFactory
import tv.superawesome.protobufs.common.services.IService
import tv.superawesome.protobufs.score.services.IScoringService
import tv.superawesome.protobufs.session.services.ISessionService
import tv.superawesome.protobufs.user.services.IUserService
import tv.superawesome.protobufs.usernames.services.IUsernameService
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 11/12/2017.
 */
class ComplianceSDK
@JvmOverloads
constructor(private val environment: NetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : IAbstractFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <S : IService> getService(type: Class<S>): S? =
            when (type) {
                IAuthService::class.java -> AuthService(environment = environment, networkTask = networkTask)
                IScoringService::class.java -> ScoreService(environment = environment, networkTask = networkTask)
                IUserActionsService::class.java -> UserActionsService(environment = environment, networkTask = networkTask)
                ISingleSignOnService::class.java -> SingleSignOnService(environment = environment, networkTask = networkTask)
                ISessionService::class.java -> SessionService(environment = environment, networkTask = networkTask)
                IUsernameService::class.java -> UsernameService(environment = environment, networkTask = networkTask)
                IUserService::class.java -> UserService(environment = environment, networkTask = networkTask)
                else -> null

            } as S?
}
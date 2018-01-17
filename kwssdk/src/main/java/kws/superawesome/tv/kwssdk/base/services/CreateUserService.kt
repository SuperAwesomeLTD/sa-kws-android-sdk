package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.responses.CreateUser
import kws.superawesome.tv.kwssdk.base.responses.Login

/**
 * Created by guilherme.mota on 11/12/2017.
 */
interface CreateUserService : BaseService {

    fun startCreateUserFlow(username: String,
                            password: String,
                            dateOfBirth: String,
                            country: String,
                            parentEmail: String,
                            callback: (user: CreateUser?, error: Throwable?) -> Unit
    )

    fun getTempAccessToken(environment: KWSNetworkEnvironment,
                           callback: (login: Login?, error: Throwable?) -> Unit
    )

    fun doUserCreation(environment: KWSNetworkEnvironment,
                       username: String,
                       password: String,
                       dateOfBirth: String,
                       country: String,
                       parentEmail: String,
                       appId: Int,
                       token: String,
                       callback: (createdUser: CreateUser?, error: Throwable?) -> Unit
    )

}
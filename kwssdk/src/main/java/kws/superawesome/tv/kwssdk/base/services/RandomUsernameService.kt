package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.responses.AppConfig
import kws.superawesome.tv.kwssdk.base.responses.RandomUsername

/**
 * Created by guilherme.mota on 29/12/2017.
 */
interface RandomUsernameService : BaseService {

    //todo kind of feel these methods should be in the User Service
    fun startRandomUsernameFlow(callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit)

    fun getAppConfig(environment: KWSNetworkEnvironment,
                     callback: (appConfig: AppConfig?, error: Throwable?) -> Unit)

    fun getRandomUsername(environment: KWSNetworkEnvironment,
                          id: Int,
                          callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit)


}
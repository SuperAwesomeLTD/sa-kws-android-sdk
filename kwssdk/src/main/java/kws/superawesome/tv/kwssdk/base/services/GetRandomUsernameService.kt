package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.responses.GetRandomUsernameResponse

/**
 * Created by guilherme.mota on 29/12/2017.
 */
interface GetRandomUsernameService : BaseService {

    fun getRandomUsername(callback: (randomUser: GetRandomUsernameResponse?, error: Throwable?) -> Unit)

}
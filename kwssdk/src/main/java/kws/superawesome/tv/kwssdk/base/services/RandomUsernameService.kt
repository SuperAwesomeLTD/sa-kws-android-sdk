package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.responses.RandomUsername

/**
 * Created by guilherme.mota on 29/12/2017.
 */
interface RandomUsernameService : BaseService {

    fun getRandomUsername(callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit)

}
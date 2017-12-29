package kws.superawesome.tv.kwssdk.base.services

/**
 * Created by guilherme.mota on 29/12/2017.
 */
interface GetRandomUsernameService : BaseService {

    fun getRandomUsername(callback: (randomUser: String?, error: Throwable?) -> Unit)

}
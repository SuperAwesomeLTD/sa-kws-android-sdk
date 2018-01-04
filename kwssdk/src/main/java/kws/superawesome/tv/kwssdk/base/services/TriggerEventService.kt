package kws.superawesome.tv.kwssdk.base.services

/**
 * Created by guilherme.mota on 04/01/2018.
 */
interface TriggerEventService : BaseService {

    fun triggerEvent(points: Int,
                     userId: Int,
                     token: String,
                     eventToken: String,
                     callback: (success: Boolean?, error: Throwable?) -> Unit)

}
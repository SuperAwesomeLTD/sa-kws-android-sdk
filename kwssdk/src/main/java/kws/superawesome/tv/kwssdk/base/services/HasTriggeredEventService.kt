package kws.superawesome.tv.kwssdk.base.services

/**
 * Created by guilherme.mota on 05/01/2018.
 */
interface HasTriggeredEventService : BaseService {

    fun hasTriggeredEvent(userId: Int,
                          eventId: Int,
                          token: String,
                          callback: (success: Boolean, error: Throwable?) -> Unit)

}
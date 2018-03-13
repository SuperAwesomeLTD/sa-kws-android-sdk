package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.models.HasTriggeredEvent

/**
 * Created by guilherme.mota on 04/01/2018.
 */
interface EventsService : BaseService {

    fun triggerEvent(points: Int,
                     userId: Int,
                     token: String,
                     eventToken: String,
                     callback: (success: Boolean?, error: Throwable?) -> Unit)


    fun hasTriggeredEvent(userId: Int,
                          eventId: Int,
                          token: String,
                          callback: (hasTriggeredEvent: HasTriggeredEvent?, error: Throwable?) -> Unit)


}
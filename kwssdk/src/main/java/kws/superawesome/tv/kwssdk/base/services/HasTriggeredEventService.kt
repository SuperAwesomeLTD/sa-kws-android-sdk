package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.responses.HasTriggeredEvent

/**
 * Created by guilherme.mota on 05/01/2018.
 */
interface HasTriggeredEventService : BaseService {

    fun hasTriggeredEvent(userId: Int,
                          eventId: Int,
                          token: String,
                          callback: (hasTriggeredEvent: HasTriggeredEvent?, error: Throwable?) -> Unit)

}
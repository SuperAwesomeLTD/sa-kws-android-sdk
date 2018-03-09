package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.score.IHasTriggeredEventModel

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class HasTriggeredEvent(@SerializedName("hasTriggeredEvent")
                             override val hasTriggeredModel: Boolean = false) : IHasTriggeredEventModel
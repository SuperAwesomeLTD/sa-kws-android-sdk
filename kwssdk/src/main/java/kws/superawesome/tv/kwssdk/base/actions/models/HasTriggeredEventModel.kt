package kws.superawesome.tv.kwssdk.base.actions.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.score.IHasTriggeredEventModel

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class HasTriggeredEventModel(@SerializedName("hasTriggeredEvent")
                             override val hasTriggeredModel: Boolean) : IHasTriggeredEventModel
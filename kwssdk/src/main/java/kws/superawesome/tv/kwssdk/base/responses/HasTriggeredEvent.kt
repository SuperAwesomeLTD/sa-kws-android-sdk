package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class HasTriggeredEvent(
        @SerializedName("hasTriggeredEvent") val hasTriggeredEvent: Boolean = false
)
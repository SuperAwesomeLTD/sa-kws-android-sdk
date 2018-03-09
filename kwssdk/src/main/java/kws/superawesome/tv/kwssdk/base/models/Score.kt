package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class Score (
        @SerializedName("score") val score: Int,
        @SerializedName("rank") val rank: Int

)
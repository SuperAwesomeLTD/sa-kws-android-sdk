package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class LeadersDetail(
        @SerializedName("user") val user: String,
        @SerializedName("score") val score: Int,
        @SerializedName("rank") val rank: Int
)
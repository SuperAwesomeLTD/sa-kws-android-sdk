package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class Leaders (
        @SerializedName("results") val results: ArrayList<LeadersDetail>,
        @SerializedName("count") val count: Int,
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int

)
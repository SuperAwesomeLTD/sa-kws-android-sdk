package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class AppData(
        @SerializedName("count") val count: Int,
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("results") val results: ArrayList<AppDataDetails>
)


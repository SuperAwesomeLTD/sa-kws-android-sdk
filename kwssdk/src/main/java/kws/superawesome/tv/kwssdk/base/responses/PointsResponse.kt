package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class PointsResponse(

        @SerializedName("totalReceived") val totalReceived: Int? = 0,
        @SerializedName("total") val total: Int? = 0,
        @SerializedName("totalPointsReceivedInCurrentApp") val totalPointsReceivedInCurrentApp: Int? = 0,
        @SerializedName("availableBalance") val availableBalance: Int? = 0,
        @SerializedName("pending") val pending: Int? = 0

)
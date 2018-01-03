package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 03/01/2018.
 */
class ApplicationPermissionsResponse(

        @SerializedName("accessAddress") val accessAddress: String? = "",
        @SerializedName("accessFirstName") val accessFirstName: String? = "",
        @SerializedName("accessLastName") val accessLastName: String? = "",
        @SerializedName("accessEmail") val accessEmail: String? = "",
        @SerializedName("accessStreetAddress") val accessStreetAddress: String? = "",
        @SerializedName("accessCity") val accessCity: String? = "",
        @SerializedName("accessPostalCode") val accessPostalCode: String? = "",
        @SerializedName("accessCountry") val accessCountry: String? = "",
        @SerializedName("sendPushNotification") val sendPushNotification: Boolean?,
        @SerializedName("sendNewsletter") val sendNewsletter: String? = "",
        @SerializedName("enterCompetitions") val enterCompetitions: String? = ""

)
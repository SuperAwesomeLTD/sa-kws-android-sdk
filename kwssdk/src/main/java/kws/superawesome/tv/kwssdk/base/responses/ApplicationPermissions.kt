package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 03/01/2018.
 */
class ApplicationPermissions(

        @SerializedName("accessAddress") val accessAddress: Boolean? = null,
        @SerializedName("accessFirstName") val accessFirstName: Boolean? = null,
        @SerializedName("accessLastName") val accessLastName: Boolean? = null,
        @SerializedName("accessEmail") val accessEmail: Boolean? = null,
        @SerializedName("accessStreetAddress") val accessStreetAddress: Boolean? = null,
        @SerializedName("accessCity") val accessCity: Boolean? = null,
        @SerializedName("accessPostalCode") val accessPostalCode: Boolean? = null,
        @SerializedName("accessCountry") val accessCountry: Boolean? = null,
        @SerializedName("sendPushNotification") val sendPushNotification: Boolean? = null,
        @SerializedName("sendNewsletter") val sendNewsletter: Boolean? = null,
        @SerializedName("enterCompetitions") val enterCompetitions: Boolean? = null

)
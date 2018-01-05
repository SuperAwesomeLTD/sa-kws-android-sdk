package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class UserAddress(
        @SerializedName("street") val street: String? = "",
        @SerializedName("city") val city: String? = "",
        @SerializedName("postCode") val postCode: String? = "",
        @SerializedName("country") val country: String? = ""
)
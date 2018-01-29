package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class AppDataDetails(
        @SerializedName ("name") val name: String,
        @SerializedName ("value") val value: Int

)
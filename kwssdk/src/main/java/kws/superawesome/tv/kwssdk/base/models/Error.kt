package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 09/01/2018.
 */
data class Error (
        @SerializedName("code") val code: Int,
        @SerializedName("codeMeaning") val codeMeaning: String,
        @SerializedName("errorMessage") val errorMessage:String,
        @SerializedName("invalid") val invalid: Invalid
)
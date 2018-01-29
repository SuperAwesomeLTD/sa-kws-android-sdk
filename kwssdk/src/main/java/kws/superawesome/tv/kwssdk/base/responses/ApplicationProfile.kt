package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class ApplicationProfile(
        @SerializedName("username") val username: String?,
        @SerializedName("customField1") val customField1: Int?,
        @SerializedName("customField2") val customField2: Int?,
        @SerializedName("avatarId") val avatarId: Int?
)
package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 12/12/2017.
 */
data class CreateUserResponse(
        @SerializedName("token") val sessionToken: String? = null,
        @SerializedName("id") val id: Int? = null)
package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 08/12/2017.
 */
data class LoginResponse(
        @SerializedName("access_token")  val token: String? = null)
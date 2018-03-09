package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 08/12/2017.
 */
data class Login(
        @SerializedName("access_token")  val token: String? = null)
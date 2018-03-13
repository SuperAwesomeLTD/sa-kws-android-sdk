package kws.superawesome.tv.kwssdk.base.models.internal

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 13/03/2018.
 */
/*internal*/
data class TokenData(@SerializedName("userId")
                              val userId: Int? = 0,

                              @SerializedName("iss")
                              val iss: String? = null,

                              @SerializedName("iat")
                              val iat: Long? = 0,

                              @SerializedName("exp")
                              val exp: Long? = 0)
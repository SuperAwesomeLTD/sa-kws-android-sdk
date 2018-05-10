package kws.superawesome.tv.kwssdk.authentication.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.authentication.models.ILoggedUserModel

/**
 * Created by guilherme.mota on 12/12/2017.
 */
data class AuthUserResponseModel(@SerializedName("token")
                                 override val token: String,

                                 @SerializedName("id")
                                 override val id: Int) : ILoggedUserModel
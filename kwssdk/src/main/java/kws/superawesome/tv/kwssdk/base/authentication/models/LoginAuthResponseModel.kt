package kws.superawesome.tv.kwssdk.base.authentication.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.auth.ILoggedUserModel

/**
 * Created by guilherme.mota on 08/12/2017.
 */
data class LoginAuthResponseModel(@SerializedName("access_token")
                                  override val token: String,

        //we get it from the token, not directly from response like in create user
                                  override val id: Int) : ILoggedUserModel
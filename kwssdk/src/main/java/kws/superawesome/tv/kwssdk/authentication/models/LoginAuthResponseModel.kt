package kws.superawesome.tv.kwssdk.authentication.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.authentication.models.ILoggedUserModel

/**
 * Created by guilherme.mota on 08/12/2017.
 */
data class LoginAuthResponseModel(@SerializedName("access_token")
                                  override val token: String,
                                  override val id: Int) : ILoggedUserModel
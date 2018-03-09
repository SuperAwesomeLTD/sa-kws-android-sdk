package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.auth.ILoggedUserModel

/**
 * Created by guilherme.mota on 12/12/2017.
 */
data class CreateUser(@SerializedName("token")
                      override val token: String,

                      @SerializedName("id")
                      override val userId: Int = 0) : ILoggedUserModel
package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.user.IApplicationProfileModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class ApplicationProfile(@SerializedName("username")
                              override val name: String?,

                              @SerializedName("customField1")
                              override val customField1: Int?,

                              @SerializedName("customField2")
                              override val customField2: Int?,

                              @SerializedName("avatarId")
                              override val avatarId: Int? ) : IApplicationProfileModel
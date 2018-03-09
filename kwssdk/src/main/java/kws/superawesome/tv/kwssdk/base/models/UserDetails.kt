package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.user.IUserDetailsModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class UserDetails(@SerializedName("id")
                       override val id: Int = 0,

                       @SerializedName("username")
                       override val name: String? = null,

                       @SerializedName("firstName")
                       override val firstName: String? = null,

                       @SerializedName("lastName")
                       override val lastName: String? = null,

                       @SerializedName("address")
                       override val address: Address? = null,

                       @SerializedName("dateOfBirth")
                       override val dateOfBirth: String? = null,

                       @SerializedName("gender")
                       override val gender: String? = null,

                       @SerializedName("language")
                       override val language: String? = null,

                       @SerializedName("email")
                       override val email: String? = null,

                       @SerializedName("hasSetParentEmail")
                       override val hasSetParentEmail: Boolean? = null,

                       @SerializedName("applicationProfile")
                       override val applicationProfile: ApplicationProfile? = null,

                       @SerializedName("applicationPermissions")
                       //TODO this
                       override val applicationPermissions: Permissions? = null,

                       @SerializedName("points")
                       override val points: Points? = null,

                       @SerializedName("createdAt")
                       override val createdAt: String? = null ) : IUserDetailsModel

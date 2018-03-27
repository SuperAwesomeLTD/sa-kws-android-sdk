package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.user.IUserDetailsModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class UserDetails(@SerializedName("id")
                       override val id: Int,

                       @SerializedName("username")
                       override val name: String?,

                       @SerializedName("firstName")
                       override val firstName: String?,

                       @SerializedName("lastName")
                       override val lastName: String?,

                       @SerializedName("address")
                       override val address: Address? = null,

                       @SerializedName("dateOfBirth")
                       override val dateOfBirth: String,

                       @SerializedName("gender")
                       override val gender: String?,

                       @SerializedName("language")
                       override val language: String?,

                       @SerializedName("email")
                       override val email: String?,

                       @SerializedName("hasSetParentEmail")
                       override val hasSetParentEmail: Boolean?,

                       @SerializedName("applicationProfile")
                       override val applicationProfile: ApplicationProfile? = null,

                       @SerializedName("applicationPermissions")
                       override val applicationPermissions: Permissions? = null,

                       @SerializedName("points")
                       override val points: Points? = null,

                       @SerializedName("createdAt")
                       override val createdAt: String) : IUserDetailsModel

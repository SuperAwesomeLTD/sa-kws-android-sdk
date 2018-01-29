package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class UserDetails(
        @SerializedName("id") val id: Int? = null,
        @SerializedName("username") val username: String? = null,
        @SerializedName("firstName") val firstName: String? = null,
        @SerializedName("lastName") val lastName: String? = null,
        @SerializedName("address") val address: UserAddress? = null,
        @SerializedName("dateOfBirth") val dateOfBirth: String? = null,
        @SerializedName("gender") val gender: String? = null,
        @SerializedName("language") val language: String? = null,
        @SerializedName("email") val email: String? = null,
        @SerializedName("hasSetParentEmail") val hasSetParentEmail: Boolean? = null,
        @SerializedName("applicationProfile") val applicationProfile: ApplicationProfile? = null,
        @SerializedName("applicationPermissions") val applicationPermissions: ApplicationPermissions? = null,
        @SerializedName("points") val points: Points? = null,
        @SerializedName("createdAt") val createdAt: String? = null

)

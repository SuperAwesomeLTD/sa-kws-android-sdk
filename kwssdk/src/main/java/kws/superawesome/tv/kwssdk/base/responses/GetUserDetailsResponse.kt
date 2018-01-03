package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class GetUserDetailsResponse(
        @SerializedName("id") val id: Int? = null,
        @SerializedName("username") val username: String? = null,
        @SerializedName("firstName") val firstName: String? = null,
        @SerializedName("lastName") val lastName: String? = null,
        @SerializedName("address") val addressResponse: UserAddressResponse? = null,
        @SerializedName("dateOfBirth") val dateOfBirth: String? = null,
        @SerializedName("gender") val gender: String? = null,
        @SerializedName("language") val language: String? = null,
        @SerializedName("email") val email: String? = null,
        @SerializedName("hasSetParentEmail") val hasSetParentEmail: Boolean? = null,
        @SerializedName("applicationProfile") val applicationProfileResponse: UserApplicationProfileResponse? = null,
        @SerializedName("applicationPermissions") val applicationPermissionsResponse: ApplicationPermissionsResponse? = null,
        @SerializedName("points") val pointsResponse: PointsResponse? = null,
        @SerializedName("createdAt") val createdAt: String? = null

)

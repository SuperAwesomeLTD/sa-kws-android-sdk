package kws.superawesome.tv.kwssdk.user.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.user.models.IUserDetailsModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class UserDetailsModel(@SerializedName("id")
                            override val id: Int,

                            @SerializedName("username")
                            override val name: String?,

                            @SerializedName("firstName")
                            override val firstName: String?,

                            @SerializedName("lastName")
                            override val lastName: String?,

                            @SerializedName("address")
                            override val address: AddressModel? = null,

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
                            override val applicationProfile: ApplicationProfileModel? = null,

                            @SerializedName("applicationPermissions")
                            override val applicationPermissions: PermissionsModel? = null,

                            @SerializedName("points")
                            override val points: PointsModel? = null,

                            @SerializedName("createdAt")
                            override val createdAt: String,

                            @SerializedName("consentAgeForCountry")
                            override val consentAgeForCountry: Int,

                            @SerializedName("isMinor")
                            override val isMinor: Boolean) : IUserDetailsModel

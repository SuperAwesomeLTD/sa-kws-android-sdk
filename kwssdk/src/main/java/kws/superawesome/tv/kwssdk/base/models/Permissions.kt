package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.permission.IPermissionModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
class Permissions(@SerializedName("sendPushNotification")
                  override val notifications: Boolean? = null,

                  @SerializedName("accessAddress")
                  override val address: Boolean? = null,

                  @SerializedName("accessFirstName")
                  override val firstName: Boolean? = null,

                  @SerializedName("accessLastName")
                  override val lastName: Boolean? = null,

                  @SerializedName("accessEmail")
                  override val email: Boolean? = null,

                  @SerializedName("accessStreetAddress")
                  override val streetAddress: Boolean? = null,

                  @SerializedName("accessCity")
                  override val city: Boolean? = null,

                  @SerializedName("accessPostalCode")
                  override val postalCode: Boolean? = null,

                  @SerializedName("accessCountry")
                  override val country: Boolean? = null,

                  @SerializedName("sendNewsletter")
                  override val newsletter: Boolean? = null,

                  @SerializedName("enterCompetitions")
                  override val competition: Boolean? = null ) : IPermissionModel
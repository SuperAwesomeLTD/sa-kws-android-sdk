package kws.superawesome.tv.kwssdk.base.user.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.permission.IPermissionModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
class Permissions(@SerializedName("sendPushNotification")
                  override val notifications: Boolean?,

                  @SerializedName("accessAddress")
                  override val address: Boolean?,

                  @SerializedName("accessFirstName")
                  override val firstName: Boolean?,

                  @SerializedName("accessLastName")
                  override val lastName: Boolean?,

                  @SerializedName("accessEmail")
                  override val email: Boolean?,

                  @SerializedName("accessStreetAddress")
                  override val streetAddress: Boolean?,

                  @SerializedName("accessCity")
                  override val city: Boolean?,

                  @SerializedName("accessPostalCode")
                  override val postalCode: Boolean?,

                  @SerializedName("accessCountry")
                  override val country: Boolean?,

                  @SerializedName("sendNewsletter")
                  override val newsletter: Boolean?,

                  @SerializedName("enterCompetitions")
                  override val competition: Boolean?) : IPermissionModel
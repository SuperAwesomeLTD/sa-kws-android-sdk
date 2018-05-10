package kws.superawesome.tv.kwssdk.user.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.user.models.IAddressModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class AddressModel(@SerializedName("street")
                        override val street: String?,

                        @SerializedName("city")
                        override val city: String?,

                        @SerializedName("postCode")
                        override val postCode: String?,

                        @SerializedName("country")
                        override val country: String?,

                        @SerializedName("countryCode")
                        override val countryCode: String?,

                        @SerializedName("countryName")
                        override val countryName: String?) : IAddressModel
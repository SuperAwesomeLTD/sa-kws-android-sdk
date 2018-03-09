package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.address.IAddressModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class Address(@SerializedName("street")
                   override val street: String? = "",

                   @SerializedName("city")
                   override val city: String? = "",

                   @SerializedName("postCode")
                   override val postCode: String? = "",

                   @SerializedName("country")
                   override val country: String? = "") : IAddressModel
package kws.superawesome.tv.kwssdk.base.models.error

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.error.IInvalidTypeErrorWrapperModel

/**
 * Created by guilherme.mota on 13/03/2018.
 */
class InvalidTypeError(@SerializedName("addressCity")
                       override val addressCity: Error?,

                       @SerializedName("addressCountry")
                       override val addressCountry: Error?,

                       @SerializedName("addressPostCode")
                       override val addressPostCode: Error?,

                       @SerializedName("addressStreet")
                       override val addressStreet: Error?,

                       @SerializedName("country")
                       override val country: Error?,

                       @SerializedName("dateOfBirth")
                       override val dateOfBirth: Error?,

                       @SerializedName("oauthClientId")
                       override val oauthClientId: Error?,

                       @SerializedName("parentEmail")
                       override val parentEmail: Error?,

                       @SerializedName("password")
                       override val password: Error?,

                       @SerializedName("permissions")
                       override val permissions: Error?,

                       @SerializedName("username")
                       override val username: Error?) : Throwable(), IInvalidTypeErrorWrapperModel
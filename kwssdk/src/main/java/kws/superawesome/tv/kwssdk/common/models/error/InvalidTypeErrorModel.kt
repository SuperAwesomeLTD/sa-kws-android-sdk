package kws.superawesome.tv.kwssdk.base.common.models.error

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.common.models.error.IInvalidTypeErrorWrapperModel

/**
 * Created by guilherme.mota on 13/03/2018.
 */
class InvalidTypeErrorModel(@SerializedName("addressCity")
                            override val addressCity: ErrorModel?,

                            @SerializedName("addressCountry")
                            override val addressCountry: ErrorModel?,

                            @SerializedName("addressPostCode")
                            override val addressPostCode: ErrorModel?,

                            @SerializedName("addressStreet")
                            override val addressStreet: ErrorModel?,

                            @SerializedName("country")
                            override val country: ErrorModel?,

                            @SerializedName("dateOfBirth")
                            override val dateOfBirth: ErrorModel?,

                            @SerializedName("oauthClientId")
                            override val oauthClientId: ErrorModel?,

                            @SerializedName("parentEmail")
                            override val parentEmail: ErrorModel?,

                            @SerializedName("password")
                            override val password: ErrorModel?,

                            @SerializedName("permissions")
                            override val permissions: ErrorModel?,

                            @SerializedName("username")
                            override val username: ErrorModel?) : Throwable(), IInvalidTypeErrorWrapperModel
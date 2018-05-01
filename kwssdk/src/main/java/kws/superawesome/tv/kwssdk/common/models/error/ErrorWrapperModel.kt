package kws.superawesome.tv.kwssdk.base.common.models.error

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.common.models.error.IErrorWrapperModel

/**
 * Created by guilherme.mota on 13/03/2018.
 */
class ErrorWrapperModel(@SerializedName("code")
                        override val code: Int?,

                        @SerializedName("codeMeaning")
                        override val codeMeaning: String?,

                        @SerializedName("errorMessage")
                        override val message: String?,

                        @SerializedName("invalid")
                        override val invalid: InvalidTypeErrorModel?,

                        @SerializedName("Error")
                        override val error: String?,

                        @SerializedName("ErrorCode")
                        override val errorCode: String?) : Throwable(), IErrorWrapperModel
package kws.superawesome.tv.kwssdk.base.models.error

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.error.IErrorWrapperModel

/**
 * Created by guilherme.mota on 13/03/2018.
 */
class ErrorWrapper(@SerializedName("code")
                   override val code: Int?,

                   @SerializedName("codeMeaning")
                   override val codeMeaning: String?,

                   @SerializedName("errorMessage")
                   override val message: String?,

                   @SerializedName("invalid")
                   override val invalid: InvalidTypeError?,

                   @SerializedName("Error")
                   override val error: String?,

                   @SerializedName("ErrorCode")
                   override val errorCode: String?) : Throwable(), IErrorWrapperModel
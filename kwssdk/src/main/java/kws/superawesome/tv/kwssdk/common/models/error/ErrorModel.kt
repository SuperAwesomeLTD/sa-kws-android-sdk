package kws.superawesome.tv.kwssdk.common.models.error

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.common.models.error.IErrorModel

/**
 * Created by guilherme.mota on 13/03/2018.
 */
class ErrorModel(@SerializedName("errorMessage")
                 override val message: String?,

                 @SerializedName("code")
                 override val code: Int?,

                 @SerializedName("codeMeaning")
                 override val codeMeaning: String?) : Throwable(), IErrorModel
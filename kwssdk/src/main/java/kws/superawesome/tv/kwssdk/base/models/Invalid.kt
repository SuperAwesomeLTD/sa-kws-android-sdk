package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 09/01/2018.
 */
data class Invalid(
        @SerializedName("parentEmail") val parentEmail: InnerError
)
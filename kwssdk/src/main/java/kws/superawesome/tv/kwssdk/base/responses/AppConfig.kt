package kws.superawesome.tv.kwssdk.base.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by guilherme.mota on 29/12/2017.
 */
data class AppConfig(
        @SerializedName("app") val appConfigAppObject: AppConfigAppObject? = null)
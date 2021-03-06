package kws.superawesome.tv.kwssdk.config.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.config.models.IAppConfigModel

/**
 * Created by guilherme.mota on 29/12/2017.
 */
data class AppConfigModel(@SerializedName("id")
                          override val id: Int,

                          @SerializedName("name")
                          override val name: String) : IAppConfigModel
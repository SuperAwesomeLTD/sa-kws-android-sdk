package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.config.IAppConfigModel

/**
 * Created by guilherme.mota on 29/12/2017.
 */
data class AppConfig(@SerializedName("id")
                     override val id: Int,

                     @SerializedName("name")
                     override val name: String?) : IAppConfigModel
package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.config.IAppConfigWrapperModel

/**
 * Created by guilherme.mota on 29/12/2017.
 */
data class AppConfigWrapper(@SerializedName("app")
                            override val app: AppConfig? = null) : IAppConfigWrapperModel

package kws.superawesome.tv.kwssdk.base.app_config.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.config.IAppConfigWrapperModel

/**
 * Created by guilherme.mota on 29/12/2017.
 */
data class AppConfigWrapperModel(@SerializedName("app")
                                 override val app: AppConfigModel? = null) : IAppConfigWrapperModel

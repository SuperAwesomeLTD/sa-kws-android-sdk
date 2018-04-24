package kws.superawesome.tv.kwssdk.base.config.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.config.models.IAppConfigWrapperModel

/**
 * Created by guilherme.mota on 29/12/2017.
 */
data class AppConfigWrapperModel(@SerializedName("app")
                                 override val app: AppConfigModel? = null) : IAppConfigWrapperModel

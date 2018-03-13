package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.appdata.IAppDataModel

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class AppData(@SerializedName("name")
                   override val name: String,

                   @SerializedName("value")
                   override  val value: Int) : IAppDataModel
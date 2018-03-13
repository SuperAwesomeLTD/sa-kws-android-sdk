package kws.superawesome.tv.kwssdk.base.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.appdata.IAppDataWrapperModel

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class AppDataWrapper(@SerializedName("count")
                          override val count: Int,

                          @SerializedName("offset")
                          override val offset: Int,

                          @SerializedName("limit")
                          override val limit: Int,

                          @SerializedName("results")
                          override val results: ArrayList<AppData>) : IAppDataWrapperModel


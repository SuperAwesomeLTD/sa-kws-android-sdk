package kws.superawesome.tv.kwssdk.actions.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.actions.models.IAppDataWrapperModel

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class AppDataWrapperModel(@SerializedName("count")
                               override val count: Int,

                               @SerializedName("offset")
                               override val offset: Int,

                               @SerializedName("limit")
                               override val limit: Int,

                               @SerializedName("results")
                               override val results: ArrayList<AppDataModel>) : IAppDataWrapperModel


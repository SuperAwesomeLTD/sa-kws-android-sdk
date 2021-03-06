package kws.superawesome.tv.kwssdk.actions.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.actions.models.IAppDataModel

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class AppDataModel(@SerializedName("name")
                        override val name: String,

                        @SerializedName("value")
                        override val value: Int) : IAppDataModel
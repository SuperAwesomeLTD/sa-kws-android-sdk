package kws.superawesome.tv.kwssdk.scoring.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.score.models.ILeaderWrapperModel

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class LeadersWrapperModel(@SerializedName("results")
                               override val results: ArrayList<LeadersModel>,

                               @SerializedName("count")
                               override val count: Int,

                               @SerializedName("offset")
                               override val offset: Int,

                               @SerializedName("limit")
                               override val limit: Int) : ILeaderWrapperModel
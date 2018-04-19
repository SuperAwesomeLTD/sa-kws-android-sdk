package kws.superawesome.tv.kwssdk.base.scoring.models

import com.google.gson.annotations.SerializedName
import kws.superawesome.tv.kwssdk.base.scoring.models.Leaders
import tv.superawesome.protobufs.models.score.ILeaderWrapperModel

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class LeadersWrapper (@SerializedName("results")
                           override val results: ArrayList<Leaders>,

                           @SerializedName("count")
                           override val count: Int,

                           @SerializedName("offset")
                           override val offset: Int,

                           @SerializedName("limit")
                           override val limit: Int) : ILeaderWrapperModel
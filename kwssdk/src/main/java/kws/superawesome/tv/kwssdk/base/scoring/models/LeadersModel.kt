package kws.superawesome.tv.kwssdk.base.scoring.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.score.ILeaderModel

/**
 * Created by guilherme.mota on 05/01/2018.
 */
data class LeadersModel(@SerializedName("user")
                        override val name: String,

                        @SerializedName("score")
                        override val score: Int,

                        @SerializedName("rank")
                        override val rank: Int) : ILeaderModel
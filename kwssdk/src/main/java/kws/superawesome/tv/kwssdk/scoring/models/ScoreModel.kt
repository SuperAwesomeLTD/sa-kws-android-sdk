package kws.superawesome.tv.kwssdk.scoring.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.score.models.IScoreModel

/**
 * Created by guilherme.mota on 08/01/2018.
 */
data class ScoreModel(@SerializedName("score")
                      override val score: Int,

                      @SerializedName("rank")
                      override val rank: Int) : IScoreModel
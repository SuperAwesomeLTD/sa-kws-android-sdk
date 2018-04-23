package kws.superawesome.tv.kwssdk.base.user.models

import com.google.gson.annotations.SerializedName
import tv.superawesome.protobufs.models.score.IPointsModel

/**
 * Created by guilherme.mota on 03/01/2018.
 */
data class PointsModel(@SerializedName("pending")
                       override val pending: Int? = 0,

                       @SerializedName("totalReceived")
                       override val received: Int? = 0,

                       @SerializedName("total")
                       override val total: Int? = 0,

                       @SerializedName("availableBalance")
                       override val balance: Int? = 0,

                       @SerializedName("totalPointsReceivedInCurrentApp")
                       override val inApp: Int? = 0) : IPointsModel
package kws.superawesome.tv.kwssdk.base.username.models

import tv.superawesome.protobufs.models.usernames.IRandomUsernameModel

/**
 * Created by guilherme.mota on 29/12/2017.
 */
data class RandomUsername(
        override val randomUsername: String) : IRandomUsernameModel
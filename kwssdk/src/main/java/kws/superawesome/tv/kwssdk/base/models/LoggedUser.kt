package kws.superawesome.tv.kwssdk.base.models

import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata

/**
 * Created by guilherme.mota on 08/12/2017.
 */
data class LoggedUser (val token: String,
                       val kwsMetaData: KWSMetadata)
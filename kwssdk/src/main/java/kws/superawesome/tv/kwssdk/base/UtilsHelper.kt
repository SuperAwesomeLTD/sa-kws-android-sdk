package kws.superawesome.tv.kwssdk.base

import kws.superawesome.tv.kwssdk.base.internal.TokenData
import tv.superawesome.samobilebase.base64.ParseBase64Task
import tv.superawesome.samobilebase.common.result.Result
import tv.superawesome.samobilebase.json.ParseJsonTask

/**
 * Created by guilherme.mota on 26/03/2018.
 */
object UtilsHelper {

    @JvmStatic
    fun getMetadataFromToken(token: String): TokenData? {

        val base64task = ParseBase64Task()
        val parse = ParseJsonTask(type = TokenData::class.java)
        val tokenResult = base64task.execute(input = token).then(parse::execute)

        return when (tokenResult) {

            is Result.success -> tokenResult.value
            is Result.error -> null

        }
    }

}
package kws.superawesome.tv.kwssdk.base.common.services

import kws.superawesome.tv.kwssdk.base.common.models.error.ErrorWrapperModel
import tv.superawesome.protobufs.common.services.IService
import tv.superawesome.samobilebase.common.result.Result
import tv.superawesome.samobilebase.json.ParseJsonTask
import tv.superawesome.samobilebase.network.INetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 13/03/2018.
 */
@PublishedApi
abstract internal class
AbstractService(protected open val environment: INetworkEnvironment,
                protected open val networkTask: NetworkTask = NetworkTask()) : IService {

    protected fun parseServerError(error: Throwable): Throwable {
        if (error.message != null) {
            val json = ParseJsonTask(type = ErrorWrapperModel::class.java)
            val serverError = json.execute(input = error.message!!)
            when (serverError) {
                is Result.success -> {
                    return serverError.value
                }
                is Result.error -> {
                    return error
                }
            }
        } else {
            return error
        }
    }
}
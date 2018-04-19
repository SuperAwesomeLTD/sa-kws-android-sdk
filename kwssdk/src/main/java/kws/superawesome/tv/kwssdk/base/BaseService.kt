package kws.superawesome.tv.kwssdk.base

import kws.superawesome.tv.kwssdk.base.error.ErrorWrapper
import tv.superawesome.protobufs.IService
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.INetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 13/03/2018.
 */
@PublishedApi
abstract internal class
BaseService(protected open val environment: INetworkEnvironment,
            protected open val networkTask: NetworkTask = NetworkTask()) : IService {

    protected fun parseServerError (error: Throwable): Throwable {
        if (error.message != null) {
            val json = ParseJsonTask(type = ErrorWrapper::class.java)
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
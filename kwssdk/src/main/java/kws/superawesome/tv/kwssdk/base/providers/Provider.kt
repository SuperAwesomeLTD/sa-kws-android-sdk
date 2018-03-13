package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.models.error.ErrorWrapper
import tv.superawesome.protobufs.IService
import tv.superawesome.samobilebase.network.NetworkEnvironment
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 13/03/2018.
 */
@PublishedApi
abstract internal class
Provider(protected open val environment: NetworkEnvironment,
         protected open val networkTask: NetworkTask = NetworkTask()) : IService {

    protected fun parseServerError(serverError: Throwable?): Throwable? {
        val message = serverError?.message
        val parseTask = ParseJsonTask()
        val parseRequest = ParseJsonRequest(rawString = message)
        val parsedError = parseTask.execute(input = parseRequest, clazz = ErrorWrapper::class.java)
        return parsedError ?: serverError
    }
}
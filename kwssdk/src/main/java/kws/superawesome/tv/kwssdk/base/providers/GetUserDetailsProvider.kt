package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.GetUserDetailsRequest
import kws.superawesome.tv.kwssdk.base.responses.GetUserDetailsResponse
import kws.superawesome.tv.kwssdk.base.services.GetUserDetailsService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 03/01/2018.
 */
@PublishedApi
internal class GetUserDetailsProvider(val environment: KWSNetworkEnvironment) : GetUserDetailsService {

    override fun getUserDetails(userId: Int, token: String, callback: (userDetailsDetails: GetUserDetailsResponse?, error: Throwable?) -> Unit) {

        val getUserDetailsNetworkRequest = GetUserDetailsRequest(
                environment = environment,
                userId = userId,
                token = token
        )

        val getUserDetailsNetworkTask = NetworkTask()
        getUserDetailsNetworkTask.execute(input = getUserDetailsNetworkRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {
                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val getUserDetailsResponse = parseTask.execute<GetUserDetailsResponse>(input = parseRequest,
                        clazz = GetUserDetailsResponse::class.java) ?: GetUserDetailsResponse()

                val username = getUserDetailsResponse.username

                //
                // send callback
                if (username != null) {
                    callback(getUserDetailsResponse, null)
                } else {
                    callback(null, Throwable("Error getting user. Username is null"))
                }

            }
            // network error case
            else {

                callback(null, networkError)
            }

        }
    }


}


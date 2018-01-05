package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.UserDetailsRequest
import kws.superawesome.tv.kwssdk.base.responses.UserDetails
import kws.superawesome.tv.kwssdk.base.services.UserDetailsService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 03/01/2018.
 */
@PublishedApi
internal class UserDetailsProvider(val environment: KWSNetworkEnvironment) : UserDetailsService {

    override fun getUserDetails(userId: Int, token: String, callback: (userDetailsDetails: UserDetails?, error: Throwable?) -> Unit) {

        val getUserDetailsNetworkRequest = UserDetailsRequest(
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
                val getUserDetailsResponse = parseTask.execute<UserDetails>(input = parseRequest,
                        clazz = UserDetails::class.java)

                val error = if (getUserDetailsResponse == null) Throwable("Error getting user details") else null;
                callback (getUserDetailsResponse, error)

            }
            // network error case
            else {

                callback(null, networkError)
            }

        }
    }


}


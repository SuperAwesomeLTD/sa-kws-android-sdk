package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.LeadersRequest
import kws.superawesome.tv.kwssdk.base.responses.Leaders
import kws.superawesome.tv.kwssdk.base.services.AppService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 05/01/2018.
 */
class AppProvider(val environment: KWSNetworkEnvironment) : AppService {

    override fun getLeaders(appId: Int, token: String, callback: (leaders: Leaders?, error: Throwable?) -> Unit) {

        val getLeadersRequest = LeadersRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        val getLeadersNetworkTask = NetworkTask()
        getLeadersNetworkTask.execute(input = getLeadersRequest) { rawString, networkError ->

            //
            // network success case
            if (rawString != null && networkError == null) {
                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val getLeadersResponse = parseTask.execute<Leaders>(input = parseRequest,
                        clazz = Leaders::class.java)

                //
                // send callback
                val error = if (getLeadersResponse == null) Throwable("Error getting user details") else null;
                callback(getLeadersResponse, error)

            }
            //
            // network failure
            else {
                callback(null, networkError)
            }

        }


    }


}
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
@PublishedApi
internal class AppProvider(val environment: KWSNetworkEnvironment) : AppService {

    override fun getLeaders(appId: Int, token: String, callback: (leaders: Leaders?, error: Throwable?) -> Unit) {

        val getLeadersNetworkRequest = LeadersRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        val getLeadersNetworkTask = NetworkTask()
        getLeadersNetworkTask.execute(input = getLeadersNetworkRequest) { getLeadersNetworkResponse ->

            //
            // network success case
            if (getLeadersNetworkResponse.response != null && getLeadersNetworkResponse.error == null) {
                val parseRequest = ParseJsonRequest(rawString = getLeadersNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getLeadersResponseObject = parseTask.execute<Leaders>(input = parseRequest,
                        clazz = Leaders::class.java)

                //
                // send callback
                val error = if (getLeadersResponseObject == null) Throwable("Error getting user details") else null;
                callback(getLeadersResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, getLeadersNetworkResponse.error)
            }

        }


    }


}
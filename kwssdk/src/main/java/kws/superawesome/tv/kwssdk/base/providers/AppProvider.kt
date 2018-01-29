package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.GetAppDataRequest
import kws.superawesome.tv.kwssdk.base.requests.LeadersRequest
import kws.superawesome.tv.kwssdk.base.requests.SetAppDataRequest
import kws.superawesome.tv.kwssdk.base.responses.AppData
import kws.superawesome.tv.kwssdk.base.responses.Leaders
import kws.superawesome.tv.kwssdk.base.services.AppService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 05/01/2018.
 */
@PublishedApi
internal class AppProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : AppService {

    override fun getLeaders(appId: Int, token: String, callback: (leaders: Leaders?, error: Throwable?) -> Unit) {

        val getLeadersNetworkRequest = LeadersRequest(
                environment = environment,
                appId = appId,
                token = token
        )

        networkTask.execute(input = getLeadersNetworkRequest) { getLeadersNetworkResponse ->

            //
            // network success case
            if (getLeadersNetworkResponse.response != null && getLeadersNetworkResponse.error == null) {
                val parseRequest = ParseJsonRequest(rawString = getLeadersNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getLeadersResponseObject = parseTask.execute<Leaders>(input = parseRequest,
                        clazz = Leaders::class.java)

                //
                // send callback
                val error = if (getLeadersResponseObject != null) null else Throwable("Error getting leader details")
                callback(getLeadersResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, getLeadersNetworkResponse.error)
            }

        }


    }

    override fun setAppData(appId: Int, userId: Int, nameValue: String, numericValue: Int, token: String, callback: (success: Boolean?, error: Throwable?) -> Unit) {

        val setAppDataNetworkRequest = SetAppDataRequest(
                environment = environment,
                appId = appId,
                userId = userId,
                nameValue = nameValue,
                numericValue = numericValue,
                token = token
        )

        networkTask.execute(input = setAppDataNetworkRequest) { setAppDataNetworkResponse ->

            callback((setAppDataNetworkResponse.status == 200 || setAppDataNetworkResponse.status == 204)
                    && setAppDataNetworkResponse.error == null, setAppDataNetworkResponse.error)

        }


    }

    override fun getAppData(appId: Int, userId: Int, token: String, callback: (appData: AppData?, error: Throwable?) -> Unit) {


        val getAppDataNetworkRequest = GetAppDataRequest(
                environment = environment,
                appId = appId,
                userId = userId,
                token = token
        )

        networkTask.execute(input = getAppDataNetworkRequest) { getAppDataNetworkResponse ->

            if (getAppDataNetworkResponse.response != null && getAppDataNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = getAppDataNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val getAppDataResponseObject = parseTask.execute<AppData>(input = parseRequest,
                        clazz = AppData::class.java)

                //
                // send callback
                val error = if (getAppDataResponseObject == null) Throwable("Error getting app data") else null
                callback(getAppDataResponseObject, error)


            } else {
                //
                //network failure
                callback(null, getAppDataNetworkResponse.error)
            }


        }


    }


}
package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.GetAppConfigRequest
import kws.superawesome.tv.kwssdk.base.requests.GetRandomUsernameRequest
import kws.superawesome.tv.kwssdk.base.responses.AppConfigAppObjectResponse
import kws.superawesome.tv.kwssdk.base.responses.AppConfigResponse
import kws.superawesome.tv.kwssdk.base.responses.GetRandomUsernameResponse
import kws.superawesome.tv.kwssdk.base.services.GetRandomUsernameService
import tv.superawesome.samobilebase.network.NetworkJSONTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 29/12/2017.
 */
@PublishedApi
internal class GetRandomUsernameProvider(val environment: KWSNetworkEnvironment) : GetRandomUsernameService {


    override fun getRandomUsername(callback: (randomUser: GetRandomUsernameResponse?, error: Throwable?) -> Unit) {


        getAppConfig(environment = environment) { appConfigAppObjectResponseObj: AppConfigAppObjectResponse?, networkError: Throwable? ->

            if (appConfigAppObjectResponseObj != null && networkError == null) {

                //get id from configs
                val id = appConfigAppObjectResponseObj.id

                //Actually get random user
                getActuallyRandomUserName(environment = environment, id = id, callback = callback)
            } else {
                //
                // network failure
                callback(null, networkError)
            }

        }


    }


    private fun getAppConfig(environment: KWSNetworkEnvironment,
                             callback: (appConfigAppObjectResponse: AppConfigAppObjectResponse?, error: Throwable?) -> Unit) {

        val networkAppConfigRequest = GetAppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        val networkAppConfigTask = NetworkJSONTask()

        networkAppConfigTask.execute(input = networkAppConfigRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val authResponse = parseTask.execute<AppConfigResponse>(input = parseRequest) ?: AppConfigResponse()

                val appConfigAppResponseObj = authResponse.appConfigAppObject

                //
                // send callback
                if (appConfigAppResponseObj != null) {
                    callback(appConfigAppResponseObj, null)
                } else {
                    callback(null, Throwable("Error - not found valid app config"))
                }

            }
            //
            // network failure
            else {
                callback(null, networkError)
            }
        }
    }


    private fun getActuallyRandomUserName(environment: KWSNetworkEnvironment,
                                          id: Int,
                                          callback: (randomUser: GetRandomUsernameResponse?, error: Throwable?) -> Unit) {

        val networkGetRandomUsernameRequest = GetRandomUsernameRequest(
                environment = environment,
                appID = id)

        val networkGetRandomUsernameTask = NetworkJSONTask()
        networkGetRandomUsernameTask.execute(input = networkGetRandomUsernameRequest) { rawString, networkError ->

            if (rawString != null && networkError == null) {

                val randomUserName = rawString.replace("\"", "")
                val getRandomUsernameResponse = GetRandomUsernameResponse(randomUserName)

                callback(getRandomUsernameResponse, null)

            } else {
                //
                //network failure
                callback(null, networkError)
            }

        }

    }

}
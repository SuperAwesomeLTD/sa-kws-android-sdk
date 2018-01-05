package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest
import kws.superawesome.tv.kwssdk.base.requests.RandomUsernameRequest
import kws.superawesome.tv.kwssdk.base.responses.AppConfigAppObject
import kws.superawesome.tv.kwssdk.base.responses.AppConfig
import kws.superawesome.tv.kwssdk.base.responses.RandomUsername
import kws.superawesome.tv.kwssdk.base.services.RandomUsernameService
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 29/12/2017.
 */
@PublishedApi
internal class RandomUsernameProvider(val environment: KWSNetworkEnvironment) : RandomUsernameService {


    override fun getRandomUsername(callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit) {


        getAppConfig(environment = environment) { appConfig: AppConfig?, networkError: Throwable? ->

            if (appConfig?.appConfigAppObject != null && networkError == null) {

                //get id from configs
                val id = appConfig.appConfigAppObject.id

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
                             callback: (appConfig: AppConfig?, error: Throwable?) -> Unit) {

        val networkAppConfigRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        val networkAppConfigTask = NetworkTask()

        networkAppConfigTask.execute(input = networkAppConfigRequest) { rawString, networkError ->

            // network success case
            if (rawString != null && networkError == null) {

                val parseRequest = ParseJsonRequest(rawString = rawString)
                val parseTask = ParseJsonTask()
                val appConfigResponse = parseTask.execute<AppConfig>(input = parseRequest,
                        clazz = AppConfig::class.java)

                //
                // send callback
                val error = if (appConfigResponse != null) null else Throwable("Error - not found valid app config")
                callback(appConfigResponse, error)

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
                                          callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit) {

        val networkGetRandomUsernameRequest = RandomUsernameRequest(
                environment = environment,
                appID = id)

        val networkGetRandomUsernameTask = NetworkTask()
        networkGetRandomUsernameTask.execute(input = networkGetRandomUsernameRequest) { rawString, networkError ->

            if (rawString != null && networkError == null) {

                val randomUserName = rawString.replace("\"", "")

                //
                // send callback
                if(randomUserName != null){
                    callback(RandomUsername(randomUserName), null)
                }else{
                    callback(RandomUsername(rawString), null)
                }


            } else {
                //
                //network failure
                callback(null, networkError)
            }

        }

    }

}
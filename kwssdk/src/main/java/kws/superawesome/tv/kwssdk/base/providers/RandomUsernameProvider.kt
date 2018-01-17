package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest
import kws.superawesome.tv.kwssdk.base.requests.RandomUsernameRequest
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
internal class RandomUsernameProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : RandomUsernameService {


    override fun startRandomUsernameFlow(callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit) {


        getAppConfig(environment = environment) { appConfig: AppConfig?, networkError: Throwable? ->

            if (appConfig?.appConfigAppObject != null && networkError == null) {

                //get id from configs
                val id = appConfig.appConfigAppObject.id

                //Actually get random user
                getRandomUsername(environment = environment, id = id, callback = callback)
            } else {
                //
                // network failure
                callback(null, networkError)
            }

        }


    }


   override fun getAppConfig(environment: KWSNetworkEnvironment,
                     callback: (appConfig: AppConfig?, error: Throwable?) -> Unit) {

        val appConfigNetworkRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        networkTask.execute(input = appConfigNetworkRequest) { appConfigNetworkResponse ->

            // network success case
            if (appConfigNetworkResponse.response != null && appConfigNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = appConfigNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val appConfigResponseObject = parseTask.execute<AppConfig>(input = parseRequest,
                        clazz = AppConfig::class.java)

                //
                // send callback
                val error = if (appConfigResponseObject != null) null else Throwable("Error - not found valid app config")
                callback(appConfigResponseObject, error)

            }
            //
            // network failure
            else {
                callback(null, appConfigNetworkResponse.error)
            }
        }
    }


    override fun getRandomUsername(environment: KWSNetworkEnvironment,
                                   id: Int,
                                   callback: (randomUser: RandomUsername?, error: Throwable?) -> Unit) {

        val getRandomUsernameNetworkRequest = RandomUsernameRequest(
                environment = environment,
                appID = id)

        networkTask.execute(input = getRandomUsernameNetworkRequest) { getRandomUsernameNetworkResponse ->

            val responseString = getRandomUsernameNetworkResponse.response

            if (responseString != null && getRandomUsernameNetworkResponse.error == null) {

                val randomUserName = responseString.replace("\"", "")

                //
                // send callback
                if (randomUserName != null) {
                    callback(RandomUsername(randomUserName), null)
                } else {
                    callback(RandomUsername(responseString), null)
                }


            } else {
                //
                //network failure
                callback(null, getRandomUsernameNetworkResponse.error)
            }

        }

    }

}
package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.AppConfigWrapper
import kws.superawesome.tv.kwssdk.base.models.RandomUsername
import kws.superawesome.tv.kwssdk.base.requests.AppConfigRequest
import kws.superawesome.tv.kwssdk.base.requests.RandomUsernameRequest
import tv.superawesome.protobufs.features.auth.IUsernameService
import tv.superawesome.protobufs.models.usernames.IRandomUsernameModel
import tv.superawesome.protobufs.models.usernames.IVerifiedUsernameModel
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask

/**
 * Created by guilherme.mota on 29/12/2017.
 */
@PublishedApi
internal class UsernameProvider
@JvmOverloads
constructor(private val environment: KWSNetworkEnvironment,
            private val networkTask: NetworkTask = NetworkTask()) : IUsernameService {

    override fun getRandomUsername(callback: (username: IRandomUsernameModel?, error: Throwable?) -> Unit) {
        getAppConfig(environment = environment) { appConfigWrapper: AppConfigWrapper?, networkError: Throwable? ->

            if (appConfigWrapper?.app != null && networkError == null) {

                //get id from configs
                appConfigWrapper.app.id?.let {

                    //Actually get random user
                    fetchRandomUsernameFromBackend(environment = environment, id = it, callback = callback)

                }
            } else {
                //
                // network failure
                callback(null, networkError)
            }

        }


    }

    private fun getAppConfig(environment: KWSNetworkEnvironment,
                             callback: (appConfigWrapper: AppConfigWrapper?, error: Throwable?) -> Unit) {

        val appConfigNetworkRequest = AppConfigRequest(
                environment = environment,
                clientID = environment.appID
        )

        networkTask.execute(input = appConfigNetworkRequest) { appConfigNetworkResponse ->

            // network success case
            if (appConfigNetworkResponse.response != null && appConfigNetworkResponse.error == null) {

                val parseRequest = ParseJsonRequest(rawString = appConfigNetworkResponse.response)
                val parseTask = ParseJsonTask()
                val appConfigResponseObject = parseTask.execute<AppConfigWrapper>(input = parseRequest,
                        clazz = AppConfigWrapper::class.java)

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


    private fun fetchRandomUsernameFromBackend(environment: KWSNetworkEnvironment,
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

    override fun verifyUsername(username: String, callback: (validation: IVerifiedUsernameModel?, error: Throwable?) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
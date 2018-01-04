package kws.superawesome.tv.kwssdk.base.providers

import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.requests.InviteUserRequest
import kws.superawesome.tv.kwssdk.base.services.InviteUserService
import tv.superawesome.samobilebase.network.NetworkTask

/**
 * Created by guilherme.mota on 04/01/2018.
 */
@PublishedApi
internal class InviteUserProvider(val environment: KWSNetworkEnvironment) : InviteUserService {


    override fun inviteUser(email: String, userId: Int, token: String, callback: (success: Boolean?, error: Throwable?) -> Unit) {

        val inviteUserRequest = InviteUserRequest(
                environment = environment,
                userId = userId,
                token = token,
                emailAddress = email
        )

        val inviteUserRequestNetworkTask = NetworkTask()
        inviteUserRequestNetworkTask.execute(input = inviteUserRequest) { rawString, networkError ->

            if (rawString.isNullOrEmpty() && networkError == null) {
                //
                // send callback
                callback(true, null)
            } else {
                callback(false, networkError)

            }

        }


    }
}
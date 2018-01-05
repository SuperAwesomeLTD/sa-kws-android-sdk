package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.responses.UserDetails

/**
 * Created by guilherme.mota on 03/01/2018.
 */
interface UserService : BaseService {

    fun getUserDetails(userId: Int,
                       token: String,
                       callback: (userDetailsDetails: UserDetails?, error: Throwable?) -> Unit)
}
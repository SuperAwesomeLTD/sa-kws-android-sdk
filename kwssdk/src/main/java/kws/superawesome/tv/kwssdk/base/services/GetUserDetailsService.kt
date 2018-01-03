package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.responses.GetUserDetailsResponse

/**
 * Created by guilherme.mota on 03/01/2018.
 */
interface GetUserDetailsService : BaseService {

    fun getUserDetails(userId: Int,
                       token: String,
                       callback: (userDetailsDetails: GetUserDetailsResponse?, error: Throwable?) -> Unit)
}
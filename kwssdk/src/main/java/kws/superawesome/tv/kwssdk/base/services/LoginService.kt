package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.models.LoggedUser

/**
 * Created by guilherme.mota on 08/12/2017.
 */
interface LoginService : BaseService {

    fun loginUser(username: String,
                  password: String,
                  callback: (user: LoggedUser?, error: Throwable?) -> Unit)

}
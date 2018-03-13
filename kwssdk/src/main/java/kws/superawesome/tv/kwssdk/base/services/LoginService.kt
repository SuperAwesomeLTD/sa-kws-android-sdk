package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse

/**
 * Created by guilherme.mota on 08/12/2017.
 */
interface LoginService : BaseService {

    fun loginUser(username: String,
                  password: String,
                  callback: (user: LoginAuthResponse?, error: Throwable?) -> Unit)

}
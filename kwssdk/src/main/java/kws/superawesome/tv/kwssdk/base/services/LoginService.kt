package kws.superawesome.tv.kwssdk.base.services

import android.app.Activity
import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.responses.Login

/**
 * Created by guilherme.mota on 08/12/2017.
 */
interface LoginService : BaseService {

    fun loginUser(username: String,
                  password: String,
                  callback: (user: Login?, error: Throwable?) -> Unit)

}
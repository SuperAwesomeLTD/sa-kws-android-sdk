package kws.superawesome.tv.kwssdk.base.services

import android.app.Activity
import kws.superawesome.tv.kwssdk.base.models.internal.LoggedUser

/**
 * Created by guilherme.mota on 26/01/2018.
 */
interface AuthService: BaseService {

    fun authUser (singleSignOnUrl: String,
                  parent: Activity,
                  callback: (user: LoggedUser?, error: Throwable?) -> Unit)


}
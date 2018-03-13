package kws.superawesome.tv.kwssdk.base.services

import android.app.Activity
import tv.superawesome.protobufs.models.auth.ILoggedUserModel

/**
 * Created by guilherme.mota on 26/01/2018.
 */
interface AuthService : BaseService {

    fun authUser(singleSignOnUrl: String,
                 parent: Activity,
                 callback: (user: ILoggedUserModel?, error: Throwable?) -> Unit)


}
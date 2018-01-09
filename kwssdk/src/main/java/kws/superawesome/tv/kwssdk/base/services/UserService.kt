package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.models.Permissions
import kws.superawesome.tv.kwssdk.base.responses.Score
import kws.superawesome.tv.kwssdk.base.responses.UserDetails
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionStatus

/**
 * Created by guilherme.mota on 03/01/2018.
 */
interface UserService : BaseService {

    fun getUserDetails(userId: Int,
                       token: String,
                       callback: (userDetailsDetails: UserDetails?, error: Throwable?) -> Unit)

    fun inviteUser(email: String,
                   userId: Int,
                   token: String,
                   callback: (success: Boolean?, error: Throwable?) -> Unit)


    fun getScore(appId: Int,
                 token: String,
                 callback: (score: Score?, error: Throwable?) -> Unit)


    fun requestPermissions(userId: Int,
                           token: String,
                           permissionsList: List<String>,
                           callback: (kwsChildrenRequestPermissionStatus: KWSChildrenRequestPermissionStatus?, error: Throwable?) -> Unit)


}
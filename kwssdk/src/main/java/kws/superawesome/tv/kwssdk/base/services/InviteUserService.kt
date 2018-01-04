package kws.superawesome.tv.kwssdk.base.services

/**
 * Created by guilherme.mota on 04/01/2018.
 */
interface InviteUserService : BaseService {

    fun inviteUser(email: String, userId: Int, token: String,
                   callback: (success: Boolean?, error: Throwable?) -> Unit)

}
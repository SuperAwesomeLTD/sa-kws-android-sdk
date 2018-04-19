package kws.superawesome.tv.kwssdk.base.internal

import tv.superawesome.protobufs.models.auth.ILoggedUserModel

/**
 * Created by guilherme.mota on 08/12/2017.
 */
/*internal*/
data class LoggedUser(override val token: String,

                      val tokenData: TokenData,

                      override val id: Int) : ILoggedUserModel {

    var registeredForRM: Boolean = false
}

package kws.superawesome.tv.kwssdk.interfaces

import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.process.KWSChildrenLoginUserStatus

/**
 * Created by guilherme.mota on 11/12/2017.
 */
interface LoggedUserInterface {

    abstract fun didLoginUser(loggedUser: LoggedUser?,
                              error: Throwable?)

}
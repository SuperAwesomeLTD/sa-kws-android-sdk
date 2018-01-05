package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.models.LoggedUser
import kws.superawesome.tv.kwssdk.base.responses.CreateUser

/**
 * Created by guilherme.mota on 11/12/2017.
 */
interface CreateUserService : BaseService {

    fun createUser(username: String,
                   password: String,
                   dateOfBirth: String,
                   country: String,
                   parentEmail: String,
                   callback: (user: CreateUser?, error: Throwable?) -> Unit
    )

}
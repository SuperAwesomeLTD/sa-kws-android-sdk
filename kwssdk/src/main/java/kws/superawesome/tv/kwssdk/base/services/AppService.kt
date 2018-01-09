package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.responses.Leaders

/**
 * Created by guilherme.mota on 05/01/2018.
 */
interface AppService : BaseService {


    fun getLeaders(appId: Int,
                   token: String,
                   callback: (leaders: Leaders?, error: Throwable?) -> Unit)

    //TODO
//    fun getAppData()


    fun setAppData(appId: Int,
                   userId: Int,
                   nameValue: String,
                   numericValue: Int,
                   token: String,
                   callback: (success: Boolean?, error: Throwable?) -> Unit)


}
package kws.superawesome.tv.kwssdk.base.services

import kws.superawesome.tv.kwssdk.base.models.AppDataWrapper
import kws.superawesome.tv.kwssdk.base.models.Leaders

/**
 * Created by guilherme.mota on 05/01/2018.
 */
interface AppService : BaseService {


    fun getLeaders(appId: Int,
                   token: String,
                   callback: (leaders: Leaders?, error: Throwable?) -> Unit)


    fun getAppData(appId: Int,
                   userId: Int,
                   token: String,
                   callback: (appDataWrapper: AppDataWrapper?, error: Throwable?) -> Unit)


    fun setAppData(appId: Int,
                   userId: Int,
                   nameValue: String,
                   numericValue: Int,
                   token: String,
                   callback: (success: Boolean?, error: Throwable?) -> Unit)


}
package kws.superawesome.tv.kwssdk.base.services

import tv.superawesome.protobufs.models.appdata.IAppDataWrapperModel
import tv.superawesome.protobufs.models.score.ILeaderWrapperModel

/**
 * Created by guilherme.mota on 05/01/2018.
 */
interface AppService : BaseService {


    fun getLeaders(appId: Int,
                   token: String,
                   callback: (leadersWrapper: ILeaderWrapperModel?, error: Throwable?) -> Unit)


    fun getAppData(appId: Int,
                   userId: Int,
                   token: String,
                   callback: (appDataWrapper: IAppDataWrapperModel?, error: Throwable?) -> Unit)


    fun setAppData(appId: Int,
                   userId: Int,
                   nameValue: String,
                   numericValue: Int,
                   token: String,
                   callback: (success: Boolean?, error: Throwable?) -> Unit)


}
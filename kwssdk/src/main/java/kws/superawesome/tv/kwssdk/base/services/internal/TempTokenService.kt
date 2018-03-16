package kws.superawesome.tv.kwssdk.base.services.internal

import kws.superawesome.tv.kwssdk.base.models.LoginAuthResponse

/**
 * Created by guilherme.mota on 16/03/2018.
 */
interface TempTokenService {

    fun getTempAccessToken(callback: (loginAuthResponse: LoginAuthResponse?, error: Throwable?) -> Unit)
}
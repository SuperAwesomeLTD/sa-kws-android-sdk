package kws.superawesome.tv.kwssdk.base.providers

import android.content.Context
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment
import kws.superawesome.tv.kwssdk.base.models.internal.LoggedUser
import kws.superawesome.tv.kwssdk.base.models.internal.TokenData
import tv.superawesome.protobufs.features.session.ISessionService
import tv.superawesome.protobufs.models.auth.ILoggedUserModel
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Request
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest
import tv.superawesome.samobilebase.parsejson.ParseJsonTask
import tv.superawesome.samobilebase.readdb.ReadDbRequest
import tv.superawesome.samobilebase.readdb.ReadDbTask
import tv.superawesome.samobilebase.writedb.WriteDbRequest
import tv.superawesome.samobilebase.writedb.WriteDbTask

/**
 * Created by guilherme.mota on 13/03/2018.
 */

@PublishedApi
internal class SessionProvider
@JvmOverloads
constructor(private val kDB_NAME: String = "KWS_DB",
            private val kUSER_TOKEN_KEY:String = "KWS_USER_TOKEN",
            override val environment: KWSNetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : Provider(environment = environment), ISessionService{

    override fun isUserLoggedIn(context: Context): Boolean = getCurrentUser(context) != null

    // TODO(Also have some in memory state so we don't always perform DB checks)
    override fun getCurrentUser(context: Context): ILoggedUserModel? {

        val sharedPreferences = context.getSharedPreferences(kDB_NAME, Context.MODE_PRIVATE)

        val dbRequest = ReadDbRequest(preferences = sharedPreferences, key = kUSER_TOKEN_KEY)
        val dbTask = ReadDbTask()
        val token = dbTask.execute<String>(input = dbRequest, clazz = String::class.java)

        val base64Request = ParseBase64Request(base64String = token)
        val base64Task = ParseBase64Task()
        val jsonToken = base64Task.execute(input = base64Request)

        val jsonRequest = ParseJsonRequest(rawString = jsonToken)
        val jsonTask = ParseJsonTask()
        val tokenData = jsonTask.execute<TokenData>(input = jsonRequest, clazz = TokenData::class.java)

        val userId = tokenData?.userId

        return try {
            LoggedUser(token = token!!, tokenData = tokenData!!, id = userId!!)
        } catch (e: Exception) {
            null
        }
    }

    override fun saveLoggedUser(context: Context,
                                user: ILoggedUserModel): Boolean {

        val sharedPreferences = context.getSharedPreferences(kDB_NAME, Context.MODE_PRIVATE)

        val token = user.token
        val dbRequest = WriteDbRequest(preferences = sharedPreferences, key = kUSER_TOKEN_KEY, value = token)
        val dbTask = WriteDbTask()

        return try {
            dbTask.execute(input = dbRequest)
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}
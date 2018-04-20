package kws.superawesome.tv.kwssdk.base.session.services

import android.content.Context
import kws.superawesome.tv.kwssdk.base.common.AbstractService
import kws.superawesome.tv.kwssdk.base.NetworkEnvironment
import kws.superawesome.tv.kwssdk.base.internal.LoggedUserModel
import kws.superawesome.tv.kwssdk.base.internal.TokenData
import tv.superawesome.protobufs.features.session.ISessionService
import tv.superawesome.protobufs.models.auth.ILoggedUserModel
import tv.superawesome.samobilebase.Result
import tv.superawesome.samobilebase.network.NetworkTask
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task
import tv.superawesome.samobilebase.parsejson.ParseJsonTask
import tv.superawesome.samobilebase.readdb.ReadDbRequest
import tv.superawesome.samobilebase.readdb.ReadDbTask
import tv.superawesome.samobilebase.writedb.WriteDbRequest
import tv.superawesome.samobilebase.writedb.WriteDbTask

/**
 * Created by guilherme.mota on 13/03/2018.
 */

@PublishedApi
internal class SessionService
@JvmOverloads
constructor(private val kDB_NAME: String = "KWS_DB",
            private val kUSER_TOKEN_KEY: String = "KWS_USER_TOKEN",
            override val environment: NetworkEnvironment,
            override val networkTask: NetworkTask = NetworkTask())
    : AbstractService(environment = environment), ISessionService {

    override fun isUserLoggedIn(context: Context): Boolean = getCurrentUser(context) != null

    // TODO(Also have some in memory state so we don't always perform DB checks)
    override fun getCurrentUser(context: Context): ILoggedUserModel? {

        val sharedPreferences = context.getSharedPreferences(kDB_NAME, Context.MODE_PRIVATE)

        val dbTask = ReadDbTask<String>()
        val base64Task = ParseBase64Task()
        val jsonTask = ParseJsonTask(type = TokenData::class.java)

        val dbRequest = ReadDbRequest(preferences = sharedPreferences, key = kUSER_TOKEN_KEY)

        val token = dbTask.execute(input = dbRequest)
        val result = token.then(base64Task::execute).then(jsonTask::execute)

        return when (result) {
            is Result.success -> {
                when (token) {

                    is Result.success -> {
                        result.value.userId?.let {
                            LoggedUserModel(token = token.value, tokenData = result.value, id = it)
                        }
                    }
                    is Result.error -> null
                }
            }
            is Result.error -> null
        }
    }

    override fun saveLoggedUser(context: Context,
                                user: ILoggedUserModel): Boolean {

        val sharedPreferences = context.getSharedPreferences(kDB_NAME, Context.MODE_PRIVATE)

        val token = user.token
        val request = WriteDbRequest(preferences = sharedPreferences, key = kUSER_TOKEN_KEY, value = token)
        val task = WriteDbTask()

        val result = task.execute(input = request)

        return when (result) {
            is Result.success -> true
            is Result.error -> false
        }
    }
}
package kws.superawesome.tv.kwssdk.base.responses

/**
 * Created by guilherme.mota on 08/12/2017.
 */
data class BaseAuthResponse(val sessionToken: String? = null,
                            val userBanned: Boolean = false,
                            val usernameAlreadyTaken: Boolean = false,
                            val usernameForbidden: Boolean = false,
                            val badCredentials: Boolean = false,
                            val message: String? = null) {

    val passwordShort get() = message?.contains("password") ?: false

}
package kws.superawesome.tv.kwssdk.base.internal

/**
 * Created by guilherme.mota on 26/01/2018.
 */

/*internal*/
data class OAuthData(
        val codeChallenge: String,
        val codeVerifier: String,
        val codeChallengeMethod: String
)
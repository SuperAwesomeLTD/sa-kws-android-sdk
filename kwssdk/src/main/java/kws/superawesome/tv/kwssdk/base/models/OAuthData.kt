package kws.superawesome.tv.kwssdk.base.models

/**
 * Created by guilherme.mota on 26/01/2018.
 */
data class OAuthData(
        val codeChallenge: String,
        val codeVerifier: String,
        val codeChallengeMethod: String
)

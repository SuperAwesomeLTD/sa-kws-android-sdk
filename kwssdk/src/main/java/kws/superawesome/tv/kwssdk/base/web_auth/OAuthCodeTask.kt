package kws.superawesome.tv.kwssdk.base.web_auth

import android.util.Base64
import android.util.Log
import kws.superawesome.tv.kwssdk.base.internal.OAuthData
import tv.superawesome.samobilebase.ITask
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


/**
 * Created by guilherme.mota on 24/01/2018.
 */
class OAuthCodeTask : ITask<Any?, OAuthData> {

    private val TAG = OAuthCodeTask::class.java.simpleName

    enum class OAuthEncoding(encoding: String) {
        UsASCII("US-ASCII")
    }

    enum class OAuthDigest(digest: String) {
        Sha256("SHA-256")
    }

    enum class OAuthChallenge(challenge: String) {
        S256("S256"),
        Plain("plain")
    }

    override fun execute(input: Any?): OAuthData {
        val codeVerifier = generateCodeVerifier()
        val codeChallenge = generateCodeChallenge(codeVerifier)
        val codeChallengeMethod = OAuthChallenge.S256.toString()

        return OAuthData(
                codeChallenge = codeChallenge,
                codeVerifier = codeVerifier,
                codeChallengeMethod = codeChallengeMethod)
    }

    private fun getBase64String(source: ByteArray): String {
        return Base64.encodeToString(source, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }


    private fun getASCIIBytes(value: String): ByteArray {
        val input: ByteArray
        try {
            val usASCII = OAuthEncoding.UsASCII.toString()
            input = value.toByteArray(charset(usASCII))
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "Could not convert string to an ASCII byte array", e)
            throw IllegalStateException("Could not convert string to an ASCII byte array", e)
        }

        return input
    }

    private fun getSHA256(input: ByteArray): ByteArray {
        val signature: ByteArray
        try {
            val md = MessageDigest.getInstance(OAuthDigest.Sha256.toString())
            md.update(input, 0, input.size)
            signature = md.digest()
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Failed to get SHA-256 signature", e)
            throw IllegalStateException("Failed to get SHA-256 signature", e)
        }

        return signature
    }

    private fun generateCodeVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return getBase64String(code)
    }

    private fun generateCodeChallenge(codeVerifier: String): String {
        val input = getASCIIBytes(codeVerifier)
        val signature = getSHA256(input)
        return getBase64String(signature)
    }


}
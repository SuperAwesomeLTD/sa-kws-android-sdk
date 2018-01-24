package kws.superawesome.tv.kwssdk.base.webauth

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


/**
 * Created by guilherme.mota on 24/01/2018.
 */
object OAuthHelper {

    private val TAG = OAuthHelper::class.java.simpleName

    private const val US_ASCII = "US-ASCII"
    private const val SHA_256 = "SHA-256"

    //type of code challenge
    const val CODE_CHALLENGE_METHOD = "S256"

    private fun getBase64String(source: ByteArray): String {
        return Base64.encodeToString(source, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }


    private fun getASCIIBytes(value: String): ByteArray {
        val input: ByteArray
        try {
            input = value.toByteArray(charset(US_ASCII))
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "Could not convert string to an ASCII byte array", e)
            throw IllegalStateException("Could not convert string to an ASCII byte array", e)
        }

        return input
    }

    private fun getSHA256(input: ByteArray): ByteArray {
        val signature: ByteArray
        try {
            val md = MessageDigest.getInstance(SHA_256)
            md.update(input, 0, input.size)
            signature = md.digest()
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Failed to get SHA-256 signature", e)
            throw IllegalStateException("Failed to get SHA-256 signature", e)
        }

        return signature
    }

    fun generateCodeVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return Base64.encodeToString(code, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }

    fun generateCodeChallenge(codeVerifier: String): String {
        val input = getASCIIBytes(codeVerifier)
        val signature = getSHA256(input)
        return getBase64String(signature)
    }

}
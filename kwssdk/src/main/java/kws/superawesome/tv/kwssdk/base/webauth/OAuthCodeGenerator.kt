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
class OAuthCodeGenerator {

    private val TAG = OAuthCodeGenerator::class.java.simpleName

    private val US_ASCII = "US-ASCII"
    private val SHA_256 = "SHA-256"
    private val CODE_CHALLENGE_METHOD = "S256"

    data class OAuthDataClass(
            val codeChallenge: String,
            val codeVerifier: String,
            val codeChallengeMethod: String
    )

    fun execute(): OAuthDataClass {
        val codeVerifier = generateCodeVerifier()
        val codeChallenge = generateCodeChallenge(codeVerifier)
        val codeChallengeMethod = CODE_CHALLENGE_METHOD

        return OAuthDataClass(
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
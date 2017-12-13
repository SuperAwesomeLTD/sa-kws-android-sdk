package kws.superawesome.tv.androidbaselib.network

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import kws.superawesome.tv.androidbaselib.AsyncTask
import kws.superawesome.tv.androidbaselib.logger.Logger
import okhttp3.*
import java.io.IOException

/**
 * Created by guilherme.mota on 12/12/2017.
 */
abstract class BaseNetworkTask : AsyncTask<NetworkRequest, String?> {

    override fun execute(input: NetworkRequest, callback: (String?, Throwable?) -> Unit) {

        val client = OkHttpClient()

        val method = input.method

        val queryString = formQueryString(query = input.parameters)
        val domain = input.environment.domain
        val endpoint = input.endpoint
        val url = "$domain$endpoint$queryString"
        val header = input.headers
        val body = input.body

        val request = setRequest(url, header, method, body)!!

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response) {

                val responseBody = response.body()

                try {
                    val responseString = responseBody!!.string()
                    Handler(Looper.getMainLooper()).post {
                        Logger.log(method = method.methodString + " to " + url, message = "Success")
                        callback(responseString, null)
                    }
                } catch (e: Throwable) {
                    Handler(Looper.getMainLooper()).post {
                        Logger.error(method = method.methodString + " to " + url, message = e.message)
                        callback(null, e)
                    }
                } finally {
                    response.close()
                }
            }

            override fun onFailure(call: Call?, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Logger.error(method = method.methodString + " to " + url, message = e.message)
                    callback(null, e)
                }
            }
        })
    }

    abstract fun setRequest(url: String, header: Map<String, String>, method: NetworkMethod,
                            body: Map<String, Any>?): Request?


    private fun formQueryString(query: Map<String, Any>?): String =
            query?.let {
                val queryItems = it.keys.map { key -> key + "=" + it[key] }

                return if (queryItems.isNotEmpty()) {
                    "?" + TextUtils.join("&", queryItems)
                } else {
                    ""
                }
            } ?: ""


    fun getHeaders(headers: Map<String, String>): Headers {

        val builder = Headers.Builder()


        headers.keys.forEach { key ->
            headers[key]?.let {
                builder.add(key, it)
            }
        }

        return builder.build()
    }

    private fun logNetwork(success: Boolean, method: NetworkMethod, url: String, error: Throwable? = null) = when (success) {
        true -> Log.d("Base-SDK", "$method -> $url")
        false -> Log.e("Base-SDK", "$method -> $url\nError: $error")
    }

}
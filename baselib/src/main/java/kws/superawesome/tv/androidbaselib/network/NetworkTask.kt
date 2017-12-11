package kws.superawesome.tv.androidbaselib.network

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import kws.superawesome.tv.androidbaselib.AsyncTask
import kws.superawesome.tv.androidbaselib.logger.Logger
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class NetworkTask : AsyncTask<NetworkRequest, String?> {


    override fun execute(input: NetworkRequest, callback: (String?, Throwable?) -> Unit) {

        val client = OkHttpClient()

        val method = input.method

        //TODO is this needed?
//        val queryString = formQueryString(query = input.parameters)
        
        val domain = input.environment.domain
        val endpoint = input.endpoint
        val url = "$domain$endpoint"
        val header = input.headers
        val body = input.body

        //TODO do we need a different url encoded request?
//        var request: Request?
//        if (input.isURLEncoded) {
//            request = setURLEncodedRequest(url, header, method, body)!!
//        } else {
//            request = setDefaultRequest(url, header, method, body)!!
//        }

        var request: Request?
        request = setDefaultRequest(url, header, method, body)!!

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

    private fun setURLEncodedRequest(url: String, header: Map<String, String>, method: NetworkMethod, body: Map<String, Any>?): Nothing? {
        //TODO
        return null

    }

    private fun setDefaultRequest(url: String, header: Map<String, String>, method: NetworkMethod, body: Map<String, Any>?): Request? {
        val request = Request
                .Builder()
                .url(url)
                .headers(getHeaders(header))
                .method(method.methodString, getBody(body))
                .build()
        return request
    }

    private fun formQueryString(query: Map<String, Any>?): String =
            query?.let {
                val queryItems = it.keys.map { key -> key + "=" + it[key] }

                return if (queryItems.isNotEmpty()) {
                    "?" + TextUtils.join("&", queryItems)
                } else {
                    ""
                }
            } ?: ""

    private fun getBody(body: Map<String, Any>?): RequestBody? = body?.let {
        val json = JSONObject(it)
        val mediaType = MediaType.parse("application/json; charset=utf-8");
        val requestBody = RequestBody.create(mediaType, json.toString(2))
        return requestBody
    }

    private fun getHeaders(headers: Map<String, String>): Headers {

        val builder = Headers.Builder()


        headers.keys.forEach { key ->
            headers[key]?.let {
                builder.add(key, it)
            }
        }

        return builder.build()
    }

    private fun logNetwork(success: Boolean, method: NetworkMethod, url: String, error: Throwable? = null) = when (success) {
        true -> Log.d("PopJam-SDK", "$method -> $url")
        false -> Log.e("PopJam-SDK", "$method -> $url\nError: $error")
    }
}
package kws.superawesome.tv.androidbaselib.network

import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class NetworkJSONTask : BaseNetworkTask() {

    //default JSON request implementation
    override fun setRequest(url: String, header: Map<String, String>, method: NetworkMethod, body: Map<String, Any>?, mediaType: String): Request? {
        val request = Request
                .Builder()
                .url(url)
                .headers(getHeaders(header))
                .method(method.methodString, getBody(body, mediaType))
                .build()
        return request
    }

    private fun getBody(body: Map<String, Any>?, mediaType: String): RequestBody? =
            body?.let {
                val json = JSONObject(it)
                val mediaType = MediaType.parse(mediaType)
                val requestBody = RequestBody.create(mediaType, json.toString(2))
                return requestBody

            }


}
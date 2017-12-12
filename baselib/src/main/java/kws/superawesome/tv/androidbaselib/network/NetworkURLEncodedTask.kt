package kws.superawesome.tv.androidbaselib.network

import okhttp3.FormBody
import okhttp3.Request

/**
 * Created by guilherme.mota on 12/12/2017.
 */
class NetworkURLEncodedTask : BaseNetworkTask() {


    //URL encoded request implementation
    override fun setRequest(url: String, header: Map<String, String>, method: NetworkMethod, body: Map<String, Any>?, mediaType: String): Request? {

        // Initialize Builder
        val builder = FormBody.Builder()

        // Add Params to Builder
        body?.let {
            for ((key, value) in body) {
                builder.add(key, value as String?)
            }
        }

        //create RequestBody
        val requestBody = builder
                .build()

        //return of Request
        return Request.Builder()
                .headers(getHeaders(header))
                .url(url)
                .post(requestBody)
                .build()


    }


}
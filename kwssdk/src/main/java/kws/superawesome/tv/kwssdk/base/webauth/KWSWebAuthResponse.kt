package kws.superawesome.tv.kwssdk.base.webauth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

/**
 * Created by gabriel.coman on 13/12/2017.
 */
class KWSWebAuthResponse: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //
        // get intent & action
        val intent = intent
        val action = intent.action

        //
        // get all the needed intent data as well as the intent's cheme (to check this activity
        // was created by us for us)
        val data = intent.data
        val scheme = data.scheme
        val packName = packageName

        if (action == Intent.ACTION_VIEW && scheme == packName) {

            //
            // get the fragment (the part after the # of the url)
            val fragment = data.fragment

            //
            // get all the parts of the token
            val parts = try {
                TextUtils.split(fragment, "=")
            } catch (e: Exception) {
                null
            }

            //
            // check for parts array validity
            val token = if (parts != null && parts.size >= 2) {
                parts[1]
            } else {
                null
            }

            //
            // send back callback
            callback?.invoke(token)
        }
    }

    override fun onStart() {
        super.onStart()
        finish()
    }

    companion object {
        internal var callback: ((token: String?) -> Unit)? = null
    }
}
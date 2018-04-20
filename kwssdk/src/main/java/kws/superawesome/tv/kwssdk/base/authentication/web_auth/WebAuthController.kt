package kws.superawesome.tv.kwssdk.base.authentication.web_auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

/**
 * Created by gabriel.coman on 13/12/2017.
 */
class WebAuthController: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //
        // get the current intent
        val intent = intent

        //
        // get the action
        val action = try {
            intent.action
        } catch (e: Exception) {
            null
        }

        //
        // get the data
        val data = try {
            intent.data
        } catch (e: Exception) {
            null
        }

        //
        // get the scheme
        val scheme = try {
            data?.scheme
        } catch (e: Exception) {
            null
        }

        //
        // get the package name
        val packName = packageName

        //
        // verify:
        //  - data is valid
        //  - action is valid
        //  - scheme is valid
        //  - action is ACTION_VIEW
        //  - scheme is the package name
        if (data != null && action != null && scheme != null && action == Intent.ACTION_VIEW && scheme == packName) {

            //
            // get the fragment (data as a string)
            val dataString = intent.dataString

            //
            // get the actual token
            val parts = try {
                TextUtils.split(dataString, "=")
            } catch (e: Exception) {
                null
            }

            //
            // check for parts array validity
            val authCode = if (parts != null && parts.size >= 2) {
                parts[1]
            } else {
                null
            }

            //
            // send back callback
            callback?.invoke(authCode)
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
package kws.superawesome.tv.androidbaselib.logger

import android.util.Log

class Logger {

    companion object {

        @JvmStatic
        fun log (method: String?, message: String?) {
            Log.d("PopJam-SDK", "Info:\nMethod: $method\nMsg: $message")
        }

        @JvmStatic
        fun error (method: String?, message: String?) {
            Log.e("PopJam-SDK", "Error\nMethod: $method\nMsg: $message")
        }

        @JvmStatic
        fun warning (method: String?, message: String?) {
            Log.w("PopJam-SDK", "Warning\nMethod: $method\nMsg: $message")
        }
    }
}
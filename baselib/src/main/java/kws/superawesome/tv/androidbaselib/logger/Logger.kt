package kws.superawesome.tv.androidbaselib.logger

import android.util.Log

class Logger {

    companion object {

        @JvmStatic
        fun log (method: String?, message: String?) {
            Log.d("Base-SDK", "Info:\nMethod: $method\nMsg: $message")
        }

        @JvmStatic
        fun error (method: String?, message: String?) {
            Log.e("Base-SDK", "Error\nMethod: $method\nMsg: $message")
        }

        @JvmStatic
        fun warning (method: String?, message: String?) {
            Log.w("Base-SDK", "Warning\nMethod: $method\nMsg: $message")
        }
    }
}
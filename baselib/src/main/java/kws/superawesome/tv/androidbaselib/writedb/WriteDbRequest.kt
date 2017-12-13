package kws.superawesome.tv.androidbaselib.writedb

import android.content.Context
import kws.superawesome.tv.androidbaselib.Request

data class WriteDbRequest(val context: Context,
                          val key: String,
                          val value: Any): Request
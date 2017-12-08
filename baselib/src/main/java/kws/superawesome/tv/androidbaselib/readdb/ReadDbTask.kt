package kws.superawesome.tv.androidbaselib.readdb

import android.preference.PreferenceManager
import kws.superawesome.tv.androidbaselib.Task
import java.lang.ClassCastException

class ReadDbTask: Task<ReadDbRequest, Any?> {

    // @WARN:   This class will call out an exception when you're trying to do things like:
    //              - reading a non supported type
    //              - trying to read an Int as String
    @Throws(ClassCastException::class, IllegalArgumentException::class)
    inline fun <reified A: Any> execute(input: ReadDbRequest): A? {

        val context = input.context
        val key = input.key
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        return when (A::class) {
            String::class -> preferences.getString(key, null) as A?
            Int::class -> preferences.getInt(key, 0) as A?
            Boolean::class -> preferences.getBoolean(key, false) as A?
            Float::class -> preferences.getFloat(key, 0.0F) as A?
            Long::class -> preferences.getLong(key, 0L) as A?
            else -> throw IllegalArgumentException("Reading " + A::class.java + " is not supported")
        }
    }
}
package kws.superawesome.tv.androidbaselib.writedb

import android.preference.PreferenceManager
import kws.superawesome.tv.androidbaselib.SyncTask

class WriteDbTask: SyncTask<WriteDbRequest, Boolean> {

    // @WARN:   This class will only throw an exception if you're trying to write a non-supported
    //          type into the database
    @Throws(IllegalArgumentException::class)
    override fun execute(input: WriteDbRequest): Boolean {

        val context = input.context
        val key = input.key
        val value = input.value
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()

        when (value) {
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IllegalArgumentException("Saving " + value.javaClass + " is not yet supported")
        }

        return editor.commit()
    }
}
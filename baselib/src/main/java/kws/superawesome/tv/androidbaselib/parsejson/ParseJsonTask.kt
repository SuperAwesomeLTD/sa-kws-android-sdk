package kws.superawesome.tv.androidbaselib.parsejson

import com.google.gson.Gson
import com.google.gson.JsonParseException
import kws.superawesome.tv.androidbaselib.Task

class ParseJsonTask: Task<ParseJsonRequest, Any?> {

    inline fun <reified A: Any> execute(input: ParseJsonRequest): A? =
            try {
                Gson().fromJson<A>(input.rawString, A::class.java)
            } catch (e: JsonParseException) {
                null
            }
}
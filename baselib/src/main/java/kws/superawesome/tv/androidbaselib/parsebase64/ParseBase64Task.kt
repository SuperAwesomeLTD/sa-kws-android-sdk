package kws.superawesome.tv.androidbaselib.parsebase64

import android.util.Base64
import kws.superawesome.tv.androidbaselib.SyncTask
import java.lang.Exception

class ParseBase64Task: SyncTask<ParseBase64Request, String?> {

    override fun execute(input: ParseBase64Request): String? {

        val base64 = input.base64String
        val components = base64?.split(".")
        var token0 = components?.elementAtOrNull(1)

        val data: ByteArray? = try {
            Base64.decode(token0, Base64.DEFAULT)
        } catch (e: Exception) {
            try {
                token0 += "="
                Base64.decode(token0, Base64.DEFAULT)
            } catch (e2: Exception) {
                try {
                    token0 += "="
                    Base64.decode(token0, Base64.DEFAULT)
                } catch (e3: Throwable) {
                    null
                }
            }
        }

        return data?.let { String(it) }
    }
}
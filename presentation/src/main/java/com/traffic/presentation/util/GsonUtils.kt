package com.traffic.presentation.util

//import com.google.gson.Gson

object GsonUtils {
    fun toJson(value: Any?): String {
        //return Gson().toJson(value)
        return ""
    }

    inline fun <reified T> fromJson(value: String?) : T? {
        /*return kotlin.runCatching {
            Gson().fromJson(value, T::class.java)
        }.getOrNull()*/
        return null
    }
}
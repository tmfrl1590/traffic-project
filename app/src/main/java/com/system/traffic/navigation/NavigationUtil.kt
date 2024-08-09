package com.system.traffic.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavBackStackEntry?.fromRoute(): Screens {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")?.substringAfterLast(".")?.let {
        return when(it){
            Screens.Splash::class.simpleName -> Screens.Splash
            Screens.Home::class.simpleName -> Screens.Home
            Screens.Station::class.simpleName -> Screens.Station
            Screens.Line::class.simpleName -> Screens.Line
            Screens.Setting::class.simpleName -> Screens.Setting
            Screens.BusArrive::class.simpleName -> Screens.BusArrive("")
            else -> Screens.Splash
        }
    }
    return Screens.Splash
}

inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}
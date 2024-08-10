package com.silver.navigation

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
            com.silver.navigation.Screens.Splash::class.simpleName -> com.silver.navigation.Screens.Splash
            com.silver.navigation.Screens.Home::class.simpleName -> com.silver.navigation.Screens.Home
            com.silver.navigation.Screens.Station::class.simpleName -> com.silver.navigation.Screens.Station
            com.silver.navigation.Screens.Line::class.simpleName -> com.silver.navigation.Screens.Line
            com.silver.navigation.Screens.Setting::class.simpleName -> com.silver.navigation.Screens.Setting
            com.silver.navigation.Screens.BusArrive::class.simpleName -> com.silver.navigation.Screens.BusArrive(
                ""
            )
            else -> com.silver.navigation.Screens.Splash
        }
    }
    return com.silver.navigation.Screens.Splash
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
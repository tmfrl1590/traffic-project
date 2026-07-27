package com.traffic.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute

fun NavBackStackEntry?.fromBottomRoute(): BottomBarScreen {
    val destination = this?.destination ?: return BottomBarScreen.Home

    return when {
        destination.hasRoute(Screens.Home::class) -> BottomBarScreen.Home
        destination.hasRoute(Screens.Station::class) -> BottomBarScreen.Station
        destination.hasRoute(Screens.Setting::class) -> BottomBarScreen.Setting
        else -> BottomBarScreen.Home
    }
}
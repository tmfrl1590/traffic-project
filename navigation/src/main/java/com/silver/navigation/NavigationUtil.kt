package com.silver.navigation

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry?.fromBottomRoute(): BottomBarScreen{
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")?.substringAfterLast(".")?.let {
        return when (it) {
            BottomBarScreen.Home::class.simpleName -> BottomBarScreen.Home
            BottomBarScreen.Station::class.simpleName -> BottomBarScreen.Station
            //BottomBarScreen.Line::class.simpleName -> BottomBarScreen.Line
            //BottomBarScreen.Map::class.simpleName -> BottomBarScreen.Map
            BottomBarScreen.Setting::class.simpleName -> BottomBarScreen.Setting
            else -> BottomBarScreen.Home
        }
    }
    return BottomBarScreen.Home
}
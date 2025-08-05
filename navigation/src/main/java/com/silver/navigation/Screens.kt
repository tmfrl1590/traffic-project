package com.silver.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EvStation
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LineAxis
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Splash: Screens
    @Serializable
    data object Main: Screens
    @Serializable
    data object Home: Screens
    @Serializable
    data object Station: Screens
    @Serializable
    data object Line: Screens
    @Serializable
    data object Map: Screens
    @Serializable
    data object Setting: Screens
    @Serializable
    data class BusArrive(val arsId: String, val busStopId: String): Screens
}

sealed class BottomBarScreen (
    val screen: Screens,
    val name: String,
    val icon: ImageVector,
){
    data object Home: BottomBarScreen(
        screen = Screens.Home,
        name = "홈",
        icon = Icons.Filled.Home
    )
    data object Station: BottomBarScreen(
        screen = Screens.Station,
        name = "검색",
        icon = Icons.Filled.Search
    )
    data object Setting: BottomBarScreen(
        screen = Screens.Setting,
        name = "설정",
        icon = Icons.Filled.Settings
    )
}

val bottomDestinations = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Station,
    BottomBarScreen.Setting,
)

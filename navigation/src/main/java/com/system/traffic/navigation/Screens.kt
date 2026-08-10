package com.system.traffic.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.system.traffic.design.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screens: NavKey {
    @Serializable
    data object Splash: Screens
    @Serializable
    data object Home: Screens
    @Serializable
    data object Station: Screens
    @Serializable
    data object Setting: Screens
    @Serializable
    data class BusArrive(val arsId: String, val busStopId: String): Screens
    @Serializable
    data class LineStation(val lineId: String): Screens
}

data class BottomNavItem(
    val icon: ImageVector,
    @StringRes val titleRes: Int,
    @StringRes val topBarTitleRes: Int,
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Screens.Home to BottomNavItem(Icons.Default.Home, R.string.nav_title_home, R.string.top_bar_title_home),
    Screens.Station to BottomNavItem(Icons.Default.Search, R.string.nav_title_station, R.string.top_bar_title_station),
    Screens.Setting to BottomNavItem(Icons.Default.Person, R.string.nav_title_setting, R.string.top_bar_title_setting),
)

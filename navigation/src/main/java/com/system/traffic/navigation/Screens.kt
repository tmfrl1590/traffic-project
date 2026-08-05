package com.system.traffic.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.system.traffic.design.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface Screens: NavKey {
    @Serializable
    data object Splash: Screens
    @Serializable
    data object Main: Screens
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

val MAIN_LEVEL_ROUTES = setOf(
    Screens.Splash,
    Screens.Main,
)

val serializersConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(baseClass = NavKey::class) {
            subclass(Screens.Splash::class, serializer = Screens.Splash.serializer())
            subclass(Screens.Main::class, serializer = Screens.Main.serializer())
            subclass(Screens.Home::class, serializer = Screens.Home.serializer())
            subclass(Screens.Station::class, serializer = Screens.Station.serializer())
            subclass(Screens.Setting::class, serializer = Screens.Setting.serializer())
            subclass(Screens.BusArrive::class, serializer = Screens.BusArrive.serializer())
            subclass(Screens.LineStation::class, serializer = Screens.LineStation.serializer())
        }
    }
}
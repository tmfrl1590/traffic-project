package com.traffic.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
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
    val title: String,
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Screens.Home to BottomNavItem(
        icon = Icons.Default.Home,
        title = "홈"
    ),
    Screens.Station to BottomNavItem(
        icon = Icons.Default.Person,
        title = "검색"
    ),
    Screens.Setting to BottomNavItem(
        icon = Icons.Default.Person,
        title = "설정"
    ),
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
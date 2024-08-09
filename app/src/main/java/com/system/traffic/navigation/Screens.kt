package com.system.traffic.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EvStation
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LineAxis
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

interface ArgInterface {
    val title: String
    val icon: ImageVector?
}

sealed interface Screens : ArgInterface {
    @Serializable
    data object Splash: Screens {
        override val title: String = NavigationTitle.SPLASH
        override val icon: ImageVector? = null
    }
    @Serializable
    data object Home: Screens {
        override val title: String = NavigationTitle.MAIN_HOME
        override val icon: ImageVector = Icons.Filled.Home
    }
    @Serializable
    data object Station: Screens {
        override val title: String = NavigationTitle.MAIN_STATION
        override val icon: ImageVector = Icons.Filled.EvStation
    }
    @Serializable
    data object Line: Screens {
        override val title: String = NavigationTitle.MAIN_LINE
        override val icon: ImageVector = Icons.Filled.LineAxis
    }
    @Serializable
    data object Setting: Screens {
        override val title: String = NavigationTitle.MAIN_SETTING
        override val icon: ImageVector = Icons.Filled.Settings
    }
    @Serializable
    data class BusArrive(val stationId: Int): Screens {
        override val title: String = NavigationTitle.BUS_ARRIVE
        override val icon: ImageVector? get() = null
    }
}
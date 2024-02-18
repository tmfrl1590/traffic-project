package com.system.traffic.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Route
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.system.traffic.navigation.NavigationRouteName.MAIN_LIKE
import com.system.traffic.navigation.NavigationRouteName.MAIN_LINE
import com.system.traffic.navigation.NavigationRouteName.MAIN_STATION
import com.system.traffic.util.GsonUtils

sealed class MainNav(override val route: String, val selectedIcon : ImageVector, val unselectedIcon: ImageVector, override val title: String): Destination {
    object LIKE: MainNav(MAIN_LIKE, Icons.Filled.Home, Icons.Outlined.Home,
        NavigationTitle.MAIN_LIKE
    )
    object STATION: MainNav(MAIN_STATION, Icons.Filled.DirectionsBus, Icons.Outlined.DirectionsBus,
        NavigationTitle.MAIN_STATION
    )
    object LINE: MainNav(MAIN_LINE, Icons.Filled.Route, Icons.Outlined.Route,
        NavigationTitle.MAIN_LINE
    )

    override val deepLinks: List<NavDeepLink> = listOf(
        //navDeepLink { uriPattern = "$DEEP_LINK_SCHEME${SearchNav.route}" }
    )
}

sealed class SplashNav(override val route: String, override val title: String): Destination {
    object SPLASH: SplashNav(NavigationTitle.SPLASH, NavigationTitle.SPLASH)

    override val deepLinks: List<NavDeepLink> = listOf(
        //navDeepLink { uriPattern = "$DEEP_LINK_SCHEME${SearchNav.route}" }
    )
}

object BusArriveNav: DestinationArg<String> {

    override val route: String = NavigationRouteName.BUS_ARRIVE
    override val title: String = NavigationTitle.BUS_ARRIVE
    override val argName: String = NavigationRouteName.BUS_ARRIVE

    override val deepLinks: List<NavDeepLink> = listOf(

    )
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName){ type = NavType.StringType}
    )

    override fun navigateWithArg(item: String): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }

    override fun findArgument(navBackStackEntry: NavBackStackEntry): String? {
        val busArriveString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<String>(busArriveString)
    }
}

interface Destination {
    val route: String
    val title: String
    val deepLinks: List<NavDeepLink>
}

interface DestinationArg<T>: Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}

object NavigationRouteName{
    const val SPLASH = "splash"
    const val MAIN_LIKE = "main_home"
    const val MAIN_STATION = "main_station"
    const val MAIN_LINE = "main_line"
    const val BUS_ARRIVE = "bus_arrive"
}

object NavigationTitle{
    const val SPLASH = "스플래시"
    const val MAIN_LIKE = "홈"
    const val MAIN_STATION = "버스"
    const val MAIN_LINE = "노선"
    const val BUS_ARRIVE = "bus_arrive"
}

object Graph {
    const val ROOT = "root_graph"
    const val SPLASH = "splash_graph"
    const val HOME = "home_graph"
}
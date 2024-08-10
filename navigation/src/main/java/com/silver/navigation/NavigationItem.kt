package com.silver.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import com.silver.navigation.NavigationRouteName.MAIN_HOME
import com.silver.navigation.NavigationRouteName.MAIN_LINE
import com.silver.navigation.NavigationRouteName.MAIN_SETTING
import com.silver.navigation.NavigationRouteName.MAIN_STATION

sealed class MainNav(
    override val route: String,
    val selectedIcon : ImageVector,
    val unselectedIcon: ImageVector,
    override val title: String
): Destination {
    data object HOME: MainNav(
        route = MAIN_HOME,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        title = NavigationTitle.MAIN_HOME
    )
    object STATION: MainNav(
        route = MAIN_STATION,
        selectedIcon = Icons.Filled.DirectionsBus,
        unselectedIcon = Icons.Outlined.DirectionsBus,
        title = NavigationTitle.MAIN_STATION
    )
    data object LINE: MainNav(
        route = MAIN_LINE,
        selectedIcon = Icons.Filled.Route,
        unselectedIcon = Icons.Outlined.Route,
        title = NavigationTitle.MAIN_LINE
    )

    data object SETTING: MainNav(
        route = MAIN_SETTING,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        title = NavigationTitle.MAIN_SETTING
    )

    override val deepLinks: List<NavDeepLink> = listOf(
        //navDeepLink { uriPattern = "$DEEP_LINK_SCHEME${SearchNav.route}" }
    )
}

sealed class SplashNav(override val route: String, override val title: String): Destination {
    data object SPLASH: SplashNav(NavigationRouteName.SPLASH, NavigationTitle.SPLASH)

    override val deepLinks: List<NavDeepLink> = listOf(
        //navDeepLink { uriPattern = "$DEEP_LINK_SCHEME${SearchNav.route}" }
    )
}


/*object BusArriveNav: DestinationArg<String> {
    override val route: String = NavigationRouteName.BUS_ARRIVE
    override val title: String = NavigationTitle.BUS_ARRIVE
    override val argName: String = NavigationRouteName.BUS_ARRIVE

    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route/{$argName}"  }
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
}*/

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
    const val DEEP_LINK_SCHEME = "traffic://"
    const val SPLASH = "splash"
    const val MAIN_HOME = "main_home"
    const val MAIN_STATION = "main_station"
    const val MAIN_LINE = "main_line"
    const val BUS_ARRIVE = "bus_arrive"
    const val MAIN_SETTING = "main_setting"
}



object Graph {
    const val ROOT = "root_graph"
    const val SPLASH = "splash_graph"
    const val HOME = "home_graph"
    const val BUS_ARRIVE = "bus_arrive_graph"
}
package com.system.traffic.navigation.screen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.system.traffic.navigation.BusArriveNav
import com.system.traffic.navigation.Graph
import com.system.traffic.navigation.MainNav
import com.system.traffic.presentation.screen.bus_arrive.BusArriveScreen
import com.system.traffic.presentation.screen.home.MainLikeScreen
import com.system.traffic.presentation.screen.line.MainLineScreen
import com.system.traffic.presentation.screen.station.MainStationScreen

@Composable
fun MainScreen(

) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MainBottomNavigationBar(navController = navController)
        }

    ) {
        it
        NavHost(
            navController = navController,
            route = Graph.HOME,
            startDestination = MainNav.LIKE.route
        ) {
            composable(route = MainNav.LIKE.route) {
                MainLikeScreen(navController)
            }
            composable(route = MainNav.STATION.route) {
                MainStationScreen(navController)
            }
            composable(route = MainNav.LINE.route) {
                MainLineScreen(navController)
            }

            composable(
                route = BusArriveNav.routWithArgName(),
                arguments = BusArriveNav.arguments,
                deepLinks = BusArriveNav.deepLinks,
            ) {
                val busArriveString = BusArriveNav.findArgument(it)
                if (busArriveString != null) {
                    BusArriveScreen(busArriveString)
                }
            }
        }
    }
}

@Composable
fun MainBottomNavigationBar(
    navController: NavHostController
) {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val bottomNavigationItems = listOf(
        MainNav.LIKE,
        MainNav.STATION,
        MainNav.LINE,
    )

    NavigationBar {
        bottomNavigationItems.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (index == navigationSelectedItem) {
                            navigationItem.selectedIcon
                        } else {
                            navigationItem.unselectedIcon
                        },
                        contentDescription = navigationItem.title,
                    )
                },
                label = {
                    Text(text = navigationItem.title)
                }
            )
        }
    }
}
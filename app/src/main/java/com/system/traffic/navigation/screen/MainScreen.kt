package com.system.traffic.navigation.screen

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.system.traffic.navigation.MainNav
import com.system.traffic.navigation.gragh.HomeGraph
import androidx.compose.material3.NavigationBarItemColors as NavigationBarItemColors

@Composable
fun MainScreen(

) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MainBottomNavigationBar(navController = navController)
        },
        containerColor = Color.White,

    ) {
        it
        HomeGraph(navController = navController)
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = bottomNavigationItems.any { it.route == currentDestination?.route }

    if(bottomBarDestination){
        NavigationBar(
            containerColor = Color.White
        ) {
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
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFFC74C25),
                    )

                )
            }
        }
    }

}
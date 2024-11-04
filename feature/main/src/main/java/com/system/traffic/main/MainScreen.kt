package com.system.traffic.main

import android.app.Activity
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.silver.navigation.BottomBarScreen
import com.silver.navigation.BottomNavigationBar
import com.silver.navigation.Screens
import com.system.traffic.main.navigation.BottomBarGraph

@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    onStationCardClick: (String) -> Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onTabClick = { bottomBarScreen ->
                    navController.navigate(bottomBarScreen.screen){
                        popUpTo(navController.graph.findStartDestination().route!!){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onBack = { currentScreen ->
                    if (currentScreen == BottomBarScreen.Home) {
                        (context as Activity).finish()
                    } else {
                        navController.navigate(Screens.Home)
                    }
                }
            )
        }
    ) { paddingValues ->
        BottomBarGraph(
            context = context,
            snackBarHostState = snackBarHostState,
            navController = navController,
            paddingValues = paddingValues,
            onStationCardClick = onStationCardClick
        )
    }
}
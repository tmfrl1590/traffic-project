package com.system.traffic.main

import android.app.Activity
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.silver.navigation.BottomBarScreen
import com.silver.navigation.BottomNavigationBar
import com.silver.navigation.Screens
import com.silver.navigation.fromBottomRoute
import com.system.traffic.main.navigation.BottomBarGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    onStationCardClick: (String) -> Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromBottomRoute()

    Scaffold(
        topBar = {
            if(currentScreen == BottomBarScreen.Station){
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "정류장 검색",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    )
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
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
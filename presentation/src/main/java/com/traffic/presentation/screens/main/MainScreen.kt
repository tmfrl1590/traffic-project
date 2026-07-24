package com.traffic.presentation.screens.main

import android.app.Activity
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.navigation.BottomBarScreen
import com.traffic.navigation.BottomNavigationBar
import com.traffic.navigation.Screens
import com.traffic.navigation.fromBottomRoute
import com.traffic.presentation.event.UiEvent
import com.traffic.presentation.screens.main.navigation.BottomBarGraph
import com.traffic.presentation.screens.main.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    onStationCardClick: (String, String) -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromBottomRoute()

    LaunchedEffect(key1 = Unit) {
        mainViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            val title = when (currentScreen) {
                BottomBarScreen.Home -> "광주버스"
                BottomBarScreen.Station -> "정류장 검색"
                BottomBarScreen.Setting -> "설정"
            }
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TrafficTheme.colors.textPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TrafficTheme.colors.mainBackground
                )
            )
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
            navController = navController,
            paddingValues = paddingValues,
            onStationCardClick = onStationCardClick
        )
    }
}
package com.system.traffic.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.system.traffic.presentation.screens.bus_arrive.BusArriveScreenRoute
import com.system.traffic.presentation.screens.line_station.LineStationScreenRoute
import com.system.traffic.presentation.screens.splash.SplashScreenRoute

@Composable
fun TrafficNavigationRoot() {
    val navigationState = rememberNavigationState(
        startRoute = Screens.Splash,
        topLevelRoutes = MAIN_LEVEL_ROUTES.toSet()
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    NavDisplay(
        modifier = Modifier
            .fillMaxSize()
        ,
        onBack = navigator::goBack,
        sizeTransform = null,
        entries = navigationState.toEntries(
            entryProvider {
                entry<Screens.Splash> {
                    SplashScreenRoute(
                        onGoHomeScreen = { navigator.navigateWithRemoveCurrentRoute(route = Screens.Main)}
                    )
                }
                entry<Screens.Main> {
                    MainScreenRoute(
                        onStationCardClick = { arsId, busStopId ->
                            navigator.navigate(route = Screens.BusArrive(arsId = arsId, busStopId = busStopId))
                        }
                    )
                }
                entry<Screens.BusArrive> { key ->
                    BusArriveScreenRoute(
                        arsId = key.arsId,
                        busStopId = key.busStopId,
                        snackBarHostState = remember { SnackbarHostState() },
                        onClickBusArriveCard = { lineId ->
                            navigator.navigate(route = Screens.LineStation(lineId = lineId))
                        }
                    )
                }
                entry<Screens.LineStation> { key ->
                    LineStationScreenRoute(
                        lineId = key.lineId,
                        snackBarHostState = remember { SnackbarHostState() }
                    )
                }
            }
        )
    )
}
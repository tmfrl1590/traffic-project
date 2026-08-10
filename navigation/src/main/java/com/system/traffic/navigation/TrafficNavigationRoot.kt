package com.system.traffic.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.system.traffic.presentation.screens.bus_arrive.BusArriveScreenRoute
import com.system.traffic.presentation.screens.line_station.LineStationScreenRoute
import com.system.traffic.presentation.screens.splash.SplashScreenRoute

@Composable
fun TrafficNavigationRoot() {

    val backStack = rememberNavBackStack(Screens.Splash)

    NavDisplay(
        modifier = Modifier
            .fillMaxSize()
        ,
        backStack = backStack,
        sizeTransform = null,
        entryDecorators = listOf(
            // 1) 상태 저장/복원 (rememberSaveable 등)
            rememberSaveableStateHolderNavEntryDecorator(),
            // 2) 각 NavEntry에 ViewModelStore 연결
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Screens.Splash> {
                SplashScreenRoute(
                    onGoHomeScreen = { backStack[backStack.lastIndex] = Screens.Main }
                )
            }
            entry<Screens.Main> {
                MainScreenRoute(
                    onStationCardClick = { arsId, busStopId ->
                        backStack.add(Screens.BusArrive(arsId = arsId, busStopId = busStopId))
                    }
                )
            }
            entry<Screens.BusArrive> { key ->
                BusArriveScreenRoute(
                    arsId = key.arsId,
                    busStopId = key.busStopId,
                    onClickBusArriveCard = { lineId ->
                        backStack.add(Screens.LineStation(lineId = lineId))
                    }
                )
            }
            entry<Screens.LineStation> { key ->
                LineStationScreenRoute(
                    lineId = key.lineId,
                )
            }
        }
    )
}
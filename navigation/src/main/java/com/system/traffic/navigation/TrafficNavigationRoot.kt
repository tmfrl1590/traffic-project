package com.system.traffic.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.system.traffic.design.R
import com.system.traffic.design.component.MainTopBar
import com.system.traffic.design.component.TrafficSnackBar
import com.system.traffic.design.ui.theme.LocalSnackBarHostState
import com.system.traffic.presentation.event.UiEvent
import com.system.traffic.presentation.screens.bus_arrive.BusArriveScreenRoute
import com.system.traffic.presentation.screens.home.HomeScreenRoute
import com.system.traffic.presentation.screens.line_station.LineStationScreenRoute
import com.system.traffic.presentation.screens.main.viewmodel.MainViewModel
import com.system.traffic.presentation.screens.setting.SettingScreenRoute
import com.system.traffic.presentation.screens.splash.SplashScreenRoute
import com.system.traffic.presentation.screens.station.StationScreenRoute

@Composable
fun TrafficNavigationRoot(
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val backStack = rememberNavBackStack(Screens.Splash)
    val snackBarHostState = LocalSnackBarHostState.current

    // 홈탭(루트)에서만 동작: 뒤로가기 더블 클릭 시 앱 종료
    DoubleBackToExitHandler(
        enabled = backStack.lastOrNull() == Screens.Home,
        snackbarHostState = snackBarHostState
    )

    LaunchedEffect(mainViewModel) {
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
        topBar = {
            if(backStack.lastOrNull() in TOP_LEVEL_DESTINATIONS.keys) {
                MainTopBar(
                    title = stringResource(id = TOP_LEVEL_DESTINATIONS[backStack.lastOrNull()]?.topBarTitleRes ?: R.string.top_bar_title_home)
                )
            }
        },
        bottomBar = {
            if(backStack.lastOrNull() in TOP_LEVEL_DESTINATIONS.keys) {
                TrafficNavigationBar(
                    selectedKey = backStack.lastOrNull() ?: Screens.Home,
                    onSelectKey = { route ->
                        // [Home] 또는 [Home, 선택탭] — route가 Home이면 set이 중복을 알아서 제거
                        backStack.apply { clear(); addAll(elements = setOf(Screens.Home, route)) }
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                TrafficSnackBar(
                    message = data.visuals.message
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
            ,
            backStack = backStack,
            sizeTransform = null,
            transitionSpec = { headUpSlideTransition() },
            popTransitionSpec = { headUpSlidePopTransition() },
            predictivePopTransitionSpec = { _ -> headUpSlidePopTransition() },
            entryDecorators = listOf(
                // 1) 상태 저장/복원 (rememberSaveable 등)
                rememberSaveableStateHolderNavEntryDecorator(),
                // 2) 각 NavEntry에 ViewModelStore 연결
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<Screens.Splash> {
                    SplashScreenRoute(
                        onGoHomeScreen = { backStack[backStack.lastIndex] = Screens.Home }
                    )
                }
                entry<Screens.Home> {
                    HomeScreenRoute(
                        onStationCardClick = { arsId, busStopId ->
                            backStack.add(Screens.BusArrive(arsId = arsId, busStopId = busStopId))
                        },
                        onGotoStation = { backStack.add(Screens.Station) }
                    )
                }
                entry<Screens.Station> {
                    StationScreenRoute(
                        onStationCardClick = { arsId, busStopId ->
                            backStack.add(Screens.BusArrive(arsId = arsId, busStopId = busStopId))
                        }
                    )
                }
                entry<Screens.Setting> {
                    SettingScreenRoute()
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
}
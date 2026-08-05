package com.system.traffic.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.system.traffic.design.R
import com.system.traffic.design.component.TrafficSnackBar
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.presentation.event.UiEvent
import com.system.traffic.presentation.screens.home.HomeScreenRoute
import com.system.traffic.presentation.screens.main.viewmodel.MainViewModel
import com.system.traffic.presentation.screens.setting.SettingScreenRoute
import com.system.traffic.presentation.screens.station.StationScreenRoute

@Composable
fun MainScreenRoute(
    onStationCardClick: (String, String) -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val navigationState = rememberNavigationState(
        startRoute = Screens.Home,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    val snackBarHostState = remember { SnackbarHostState() }

    // 뒤로가기 상태 판별용 변수 계산
    val homeStack = navigationState.backStacks[Screens.Home]
    val isOnHomeTab = navigationState.topLevelRoute == Screens.Home
    val canPopWithinHome = homeStack != null && homeStack.size > 1
    val shouldHandleExitOnHome = isOnHomeTab && !canPopWithinHome

    // 분리해 낸 뒤로가기 더블 클릭 앱 종료 핸들러 사용
    DoubleBackToExitHandler(
        enabled = shouldHandleExitOnHome,
        snackbarHostState = snackBarHostState
    )

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

    // 단순하고 맑아진 뒤로가기 액션 분기 로직
    val onBack: () -> Unit = {
        when {
            canPopWithinHome -> navigator.goBack()
            shouldHandleExitOnHome -> Unit // 핸들러에서 자체적으로 처리하므로 여기서는 무동작
            else -> navigator.goBack()
        }
    }

    Scaffold(
        topBar = {
            MainTopBar(
                title = stringResource(
                    TOP_LEVEL_DESTINATIONS[navigationState.topLevelRoute]?.topBarTitleRes
                        ?: R.string.top_bar_title_home
                )
            )
        },
        bottomBar = {
            TrafficNavigationBar(
                selectedKey = navigationState.topLevelRoute,
                onSelectKey = { route ->
                    navigator.navigate(route)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                TrafficSnackBar(
                    message = data.visuals.message
                )
            }
        }
    ){ innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            onBack = onBack,
            sizeTransform = null,
            transitionSpec = { headUpSlideTransition() },
            popTransitionSpec = { headUpSlidePopTransition() },
            predictivePopTransitionSpec = { _ -> headUpSlidePopTransition() },
            entries = navigationState.toEntries(
                entryProvider {
                    entry<Screens.Home> {
                        HomeScreenRoute(
                            onStationCardClick = onStationCardClick,
                            onGotoStation = { navigator.navigate(route = Screens.Station)}
                        )
                    }
                    entry<Screens.Station> {
                        StationScreenRoute(
                            onStationCardClick = onStationCardClick,
                        )
                    }
                    entry<Screens.Setting> {
                        SettingScreenRoute()
                    }
                }
            )
        )
    }
}

@Composable
private fun MainTopBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = TrafficTheme.typography.title,
                color = TrafficTheme.colors.textPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TrafficTheme.colors.mainBackground
        )
    )
}
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
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
    val backStack = rememberNavBackStack(Screens.Home)
    val snackBarHostState = remember { SnackbarHostState() }

    // 홈탭(루트)에서만 동작: 뒤로가기 더블 클릭 시 앱 종료
    DoubleBackToExitHandler(
        enabled = backStack.size == 1,
        snackbarHostState = snackBarHostState
    )

    // key에 viewModel 포함: VM 인스턴스가 교체돼도 새 인스턴스의 이벤트를 구독하도록 함
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
            MainTopBar(
                title = stringResource(id = TOP_LEVEL_DESTINATIONS[backStack.lastOrNull()]?.topBarTitleRes ?: R.string.top_bar_title_home)
            )
        },
        bottomBar = {
            TrafficNavigationBar(
                selectedKey = backStack.lastOrNull() ?: Screens.Home,
                onSelectKey = { route ->
                    // [Home] 또는 [Home, 선택탭] — route가 Home이면 set이 중복을 알아서 제거
                    backStack.apply { clear(); addAll(elements = setOf(Screens.Home, route)) }
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
            backStack = backStack,
            sizeTransform = null,
            transitionSpec = { headUpSlideTransition() },
            popTransitionSpec = { headUpSlidePopTransition() },
            predictivePopTransitionSpec = { _ -> headUpSlidePopTransition() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<Screens.Home> {
                    HomeScreenRoute(
                        onStationCardClick = onStationCardClick,
                        onGotoStation = { backStack.add(Screens.Station) }
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
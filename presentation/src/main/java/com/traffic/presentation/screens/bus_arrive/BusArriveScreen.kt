package com.traffic.presentation.screens.bus_arrive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.common.AdBannerView
import com.traffic.common.R
import com.traffic.common.ScaffoldBackIcon
import com.traffic.common.firebase.ScreenName
import com.traffic.common.firebase.TrackScreenView
import com.traffic.common.noRippleClickable
import com.traffic.presentation.screens.bus_arrive.action.BusArriveAction
import com.traffic.presentation.screens.bus_arrive.component.BusArriveSection
import com.traffic.presentation.screens.bus_arrive.component.NextBusStopSection
import com.traffic.presentation.screens.bus_arrive.state.BusArriveState
import com.traffic.presentation.screens.bus_arrive.viewmodel.BusArriveViewModel

@Composable
fun BusArriveScreenRoute(
    arsId: String,
    busStopId: String,
    snackBarHostState: SnackbarHostState,
    busArriveViewModel: BusArriveViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    TrackScreenView(screenName = ScreenName.BusArrive)

    val state by busArriveViewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, busStopId) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (busStopId.isNotEmpty()) {
                        busArriveViewModel.startTimer(busStopId)
                    }
                }
                Lifecycle.Event.ON_PAUSE -> {
                    busArriveViewModel.stopTimer()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            busArriveViewModel.stopTimer()
        }
    }

    // 해당 arsId 정류장 조회
    LaunchedEffect(key1 = arsId) {
        busArriveViewModel.getStationInfo(arsId)
    }

    // 해당 정류장 버스 도착 정보 조회
    LaunchedEffect(key1 = Unit) {
        busArriveViewModel.getBusArriveList(arsId = busStopId)
    }

    val error by busArriveViewModel.errorFlow.collectAsStateWithLifecycle("")
    LaunchedEffect(error) {
        if (error.isNotEmpty()) {
            snackBarHostState.showSnackbar(error)
        }
    }

    BusArriveScreen(
        state = state,
        onBackClick = onBackClick,
        onAction = busArriveViewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BusArriveScreen(
    state: BusArriveState,
    onBackClick: () -> Unit,
    onAction: (BusArriveAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "정류장 정보",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        ScaffoldBackIcon()
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )
        },
        floatingActionButton = {
            RefreshButton(
                remainingSeconds = state.remainingSeconds,
                onClickRefresh = {onAction(BusArriveAction.OnClickRefresh)}
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White),
        ) {
            AdBannerView(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(bottom = 20.dp)
            )

            BusStopAndFavoriteSection(
                busStopName = state.stationInfo.busStopName ?: "",
                selected = state.stationInfo.selected,
                onClickFavorite = { onAction(BusArriveAction.OnClickFavoriteIcon(state.stationInfo))}
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "ID : ${state.stationInfo.arsId}",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            NextBusStopSection(
                nextBusStopName = state.stationInfo.nextBusStop ?: "",
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFE5E7EB)
            )

            Spacer(modifier = Modifier.height(12.dp))

            BusArriveSection(
                isLoading = state.isLoading,
                busArriveList = state.arriveList,
            )
        }
    }
}

@Composable
private fun BusStopAndFavoriteSection(
    busStopName: String,
    selected: Boolean,
    onClickFavorite: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = busStopName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Icon(
            painter = painterResource(id = if (selected) R.drawable.icon_selected_star else R.drawable.icon_unselected_star),
            contentDescription = "Favorite",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable {
                    onClickFavorite()
                }
        )
    }
}

@Composable
private fun RefreshButton(
    remainingSeconds: Int,
    onClickRefresh: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClickRefresh,
        containerColor = Color(0xFF2563EB), // 프리미엄 딥블루
        contentColor = Color.White,
        shape = CircleShape,
        modifier = Modifier.size(52.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val progress = remainingSeconds / 30f
            CircularProgressIndicator(
                progress = { progress },
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.2f),
                strokeWidth = 3.dp,
                modifier = Modifier.size(44.dp)
            )
            Text(
                text = "$remainingSeconds",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
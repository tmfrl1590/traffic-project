package com.traffic.bus_arrive

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.bus_arrive.component.BusArriveListArea
import com.traffic.bus_arrive.component.BusArriveScreenTitle
import com.traffic.bus_arrive.component.NextBusStopArea
import com.traffic.bus_arrive.component.StationFavoriteIcon
import com.traffic.bus_arrive.viewmodel.BusArriveViewModel
import com.traffic.common.ScaffoldBackIcon
import com.traffic.common.firebase.logEvent
import com.traffic.domain.model.StationModel

@Composable
fun BusArriveScreen(
    context: Context,
    arsId: String,
    snackBarHostState: SnackbarHostState,
    busArriveViewModel: BusArriveViewModel,
    onBackClick: () -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    // 해당 arsId 정류장 조회
    LaunchedEffect(key1 = arsId) {
        busArriveViewModel.getStationInfo(arsId)
    }

    LaunchedEffect(true) {
        logEvent(context, "BusArriveScreen")
        //stationViewModel.getLikeStationList()
    }

    val error by busArriveViewModel.errorFlow.collectAsStateWithLifecycle("")
    LaunchedEffect(error) {
        if (error.isNotEmpty()) {
            snackBarHostState.showSnackbar(error)
        }
    }

    val stationModel by busArriveViewModel.stationInfo.collectAsStateWithLifecycle()

    BusArriveScreenContent(
        busArriveViewModel = busArriveViewModel,
        stationModel = stationModel,
        arsId = arsId,
        snackBarHostState = snackBarHostState,
        onBackClick = onBackClick,
        onFavoriteIconClick = onFavoriteIconClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusArriveScreenContent(
    busArriveViewModel: BusArriveViewModel,
    stationModel: StationModel,
    arsId: String,
    snackBarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    BusArriveScreenTitle(
                        stationInfo = stationModel,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        ScaffoldBackIcon()
                    }
                },
                actions = {
                    StationFavoriteIcon(
                        stationModel = stationModel,
                        onFavoriteIconClick = onFavoriteIconClick,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White),
        ) {
            NextBusStopArea(
                nextBusStopName = stationModel.nextBusStop ?: "",
            )

            Spacer(modifier = Modifier.size(20.dp))

            BusArriveListArea(
                arsId = arsId,
                busArriveViewModel = busArriveViewModel,
                snackBarHostState = snackBarHostState,
            )
        }
    }
}
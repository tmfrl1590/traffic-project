package com.traffic.bus_arrive

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.traffic.common.ScaffoldBackIcon
import com.traffic.common.firebase.logEvent
import com.traffic.domain.model.StationModel
import com.traffic.line.LineViewModel
import com.traffic.station.StationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusArriveScreen(
    context: Context,
    arsId: String,
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    busArriveViewModel: BusArriveViewModel = hiltViewModel(),
    stationViewModel: com.traffic.station.StationViewModel = hiltViewModel(),
    lineViewModel: com.traffic.line.LineViewModel = hiltViewModel(),
) {
    // 해당 arsId 정류장 조회
    LaunchedEffect(key1 = arsId) {
        stationViewModel.getStationInfo(arsId)
    }

    LaunchedEffect(true) {
        logEvent(context, "BusArriveScreen")
        stationViewModel.getLikeStationList()
    }

    val stationInfo by stationViewModel.stationInfo.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    BusArriveScreenTitle(
                        stationInfo = stationInfo,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        ScaffoldBackIcon()
                    }
                },
                actions = {
                    StationFavoriteIcon(
                        stationViewModel = stationViewModel,
                        stationInfo = stationInfo,
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
            NextBusStop(
                nextBusStopName = stationInfo.nextBusStop ?: "",
            )

            Spacer(modifier = Modifier.size(20.dp))

            BusArriveList(
                arsId = arsId,
                busArriveViewModel = busArriveViewModel,
                lineViewModel = lineViewModel,
                snackBarHostState = snackBarHostState,
            )
        }
    }
}

@Composable
private fun StationFavoriteIcon(
    stationViewModel: com.traffic.station.StationViewModel,
    stationInfo: StationModel,
) {
    IconButton(
        onClick = {
            insertOrDeleteStationInfo(
                stationModel = stationInfo,
                stationViewModel = stationViewModel,
            )
        }
    ) {
        Icon(
            imageVector = if (stationInfo.selected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
        )
    }
}

private fun insertOrDeleteStationInfo(
    stationModel: StationModel,
    stationViewModel: com.traffic.station.StationViewModel,
){
    if(stationModel.selected){
        stationViewModel.deleteLikeStation(stationModel.busStopId ?: "")
    }else {
        stationViewModel.insertLikeStation(stationModel)
    }
}
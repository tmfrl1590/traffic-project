package com.system.traffic.presentation.screen.bus_arrive

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.system.traffic.presentation.component.ScaffoldBackIcon
import com.system.traffic.presentation.screen.line.LineViewModel
import com.system.traffic.presentation.screen.station.StationViewModel
import com.system.traffic.ui.theme.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusArriveScreen(
    arsId: String,
    navController: NavController,
    busArriveViewModel: BusArriveViewModel = hiltViewModel(),
    stationViewModel: StationViewModel = hiltViewModel(),
    lineViewModel: LineViewModel = hiltViewModel(),
) {
    // 해당 arsId 정류장 조회
    LaunchedEffect(key1 = arsId) {
        stationViewModel.getStationInfo(arsId)
    }

    LaunchedEffect(true) {
        stationViewModel.getLikeStationList()
        lineViewModel.getLineColor()
    }

    val stationInfo by stationViewModel.stationInfo.collectAsState()
    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    var selectedStation by remember {
        mutableStateOf(false)
    }

    selectedStation = likeStationList.contains(stationInfo)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "${stationInfo.busstop_name} (${stationInfo.ars_id})",
                        fontSize = 20.sp,
                        color = Color.Black
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
                    IconButton(
                        onClick = {
                            if (selectedStation) {
                                stationViewModel.deleteLikeStation(arsId)
                            } else {
                                stationViewModel.insertLikeStation(stationInfo)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selectedStation) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = MainColor,
                        )
                    }
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
                nextBusStopName = stationInfo.next_busstop
            )

            Spacer(modifier = Modifier.size(20.dp))

            BusArriveList(
                arsId = arsId,
                busArriveViewModel = busArriveViewModel,
                lineViewModel = lineViewModel,
            )
        }
    }
}
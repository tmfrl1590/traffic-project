package com.traffic.bus_arrive

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.bus_arrive.component.BusArriveListArea
import com.traffic.bus_arrive.component.NextBusStopArea
import com.traffic.bus_arrive.viewmodel.BusArriveViewModel
import com.traffic.common.AdBannerView
import com.traffic.common.R
import com.traffic.common.ScaffoldBackIcon
import com.traffic.common.firebase.logEvent
import com.traffic.common.noRippleClickable
import com.traffic.domain.model.StationModel

@Composable
fun BusArriveScreen(
    context: Context,
    state: BusArriveState,
    arsId: String,
    busStopId: String,
    snackBarHostState: SnackbarHostState,
    busArriveViewModel: BusArriveViewModel,
    onBackClick: () -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    // 해당 arsId 정류장 조회
    LaunchedEffect(key1 = arsId) {
        busArriveViewModel.getStationInfo(arsId)
    }

    LaunchedEffect(key1 = Unit) {
        busArriveViewModel.getBusArriveList(busStopId)
    }

    LaunchedEffect(Unit) {
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
        state = state,
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
    state: BusArriveState,
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
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                BusStopAndFavoriteArea(
                    busStopName = stationModel.busStopName ?: "",
                    selected = stationModel.selected,
                    onClickFavorite = { onFavoriteIconClick(stationModel) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "ID : ${stationModel.arsId}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                NextBusStopArea(
                    nextBusStopName = stationModel.nextBusStop ?: "",
                )

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE5E7EB)
                )

                Spacer(modifier = Modifier.height(12.dp))

                BusArriveListArea(
                    busArriveList = state.arriveList,
                )
            }


            /*AdBannerView(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )*/
        }
    }
}

@Composable
private fun BusStopAndFavoriteArea(
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
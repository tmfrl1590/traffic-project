package com.traffic.bus_arrive.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.bus_arrive.util.busArriveScreenTitleText
import com.traffic.bus_arrive.viewmodel.BusArriveViewModel
import com.traffic.common.R
import com.traffic.common.UIState
import com.traffic.common.lineTestColor
import com.traffic.common.snackBarMessage
import com.traffic.domain.model.BusArriveModel
import com.traffic.domain.model.StationModel

@Composable
fun NextBusStopArea(
    nextBusStopName: String,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${nextBusStopName}${stringResource(R.string.bus_arrive_direction)}",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun StationFavoriteIcon(
    stationModel: StationModel,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    IconButton(
        onClick = { onFavoriteIconClick(stationModel) }
    ) {
        Icon(
            imageVector = if (stationModel.selected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = Color.Red,
        )
    }
}

@Composable
fun NoBusArrive() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(com.traffic.common.R.string.bus_arrive_no_data),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )
    }
}

@Composable
fun BusArriveListArea(
    busArriveList: List<BusArriveModel>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(
            items = busArriveList,
            key = { _, busArriveItem ->
                busArriveItem.busId!!
            }
        ) { _, item ->
            BusArriveCard(
                busArriveModel = item,
            )
        }
    }
}

@Composable
fun BusArriveListArea(
    arsId: String,
    busArriveViewModel: BusArriveViewModel,
    snackBarHostState: SnackbarHostState,
) {
    LaunchedEffect(key1 = true) {
        busArriveViewModel.getBusArriveList(arsId)
    }

    val busArriveListState by busArriveViewModel.busArriveListState.collectAsState()
    val resultBusArriveList = busArriveListState.data?.data ?: emptyList()
    val resultMessage = busArriveListState.data?.message ?: "aaa"

    LaunchedEffect(true) {
        /*busArriveViewModel.errorFlow.collectLatest {
            snackBarMessage(
                snackBarHostState,
                it
            )
        }*/
    }

    when (busArriveListState) {
        is UIState.Idle -> {}
        is UIState.Loading -> CircularProgressIndicator()
        is UIState.Success -> {
            if (resultBusArriveList.isEmpty()) {
                NoBusArrive()
            } else {
                BusArriveListArea(
                    busArriveList = resultBusArriveList,
                )
            }
        }
        is UIState.Error -> {
            snackBarMessage(
                snackBarHostState = snackBarHostState,
                message = resultMessage
            )
        }
        is UIState.Exception -> {}
    }
}

@Composable
fun BusArriveCard(
    busArriveModel: BusArriveModel,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .height(52.dp)
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = busArriveModel.lineName!!,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.height(40.dp),
                    color = lineTestColor(
                        busArriveModel.lineKind ?: "1"
                    )
                )

                Text(
                    text = "${busArriveModel.remainMin}분",
                    modifier = Modifier.wrapContentWidth(),
                    textAlign = TextAlign.End,
                    color = Color.Red
                )
            }

            Text(
                text = "${stringResource(id = R.string.bus_arrive_now)} : ${busArriveModel.busStopName} (${busArriveModel.remainStop} 정거장 전)",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun BusArriveScreenTitle(
    stationInfo: StationModel,
) {
    Text(
        text = busArriveScreenTitleText(stationInfo),
        fontSize = 20.sp,
        color = Color.Black
    )
}
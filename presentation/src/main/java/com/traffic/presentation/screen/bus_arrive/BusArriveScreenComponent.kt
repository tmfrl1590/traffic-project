package com.traffic.presentation.screen.bus_arrive

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
import androidx.compose.material3.Card
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
import com.traffic.common.UIState
import com.traffic.domain.model.BusArriveModel
import com.traffic.presentation.R
import com.traffic.presentation.screen.component.CustomLoadingBar
import com.traffic.presentation.screen.component.lineColor
import com.traffic.presentation.screen.component.snackBarMessage
import com.traffic.presentation.screen.line.LineViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NextBusStop(
    nextBusStopName: String,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${nextBusStopName}${stringResource(R.string.bus_arrive_direction)}",            fontSize = 16.sp,
            textAlign = TextAlign.Center,
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
            text = stringResource(R.string.bus_arrive_no_data),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )
    }
}

@Composable
fun BusArriveList(
    busArriveList: List<BusArriveModel>,
    lineViewModel: LineViewModel,
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
                lineViewModel = lineViewModel,
            )
        }
    }
}

@Composable
fun BusArriveList(
    arsId: String,
    busArriveViewModel: BusArriveViewModel,
    lineViewModel: LineViewModel,
    snackBarHostState: SnackbarHostState,
) {
    LaunchedEffect(key1 = true) {
        busArriveViewModel.getBusArriveList(arsId)
    }

    val busArriveListState by busArriveViewModel.busArriveListState.collectAsState()
    val resultBusArriveList = busArriveListState.data?.data ?: emptyList()
    val resultMessage = busArriveListState.data?.message ?: ""

    //val errorFlow by busArriveViewModel.errorFlow.collectAsState("")

    LaunchedEffect(true) {
        busArriveViewModel.errorFlow.collectLatest { snackBarMessage(snackBarHostState, it) }
    }

    when (busArriveListState) {
        is UIState.Idle -> {}
        is UIState.Loading -> CustomLoadingBar()
        is UIState.Success -> {
            if (resultBusArriveList.isEmpty()) {
                NoBusArrive()
            } else {
                BusArriveList(
                    busArriveList = resultBusArriveList,
                    lineViewModel = lineViewModel,
                )
            }
        }
        is UIState.Error -> {
            snackBarMessage(
                snackBarHostState = snackBarHostState,
                message = resultMessage
            )
        }
    }
}

@Composable
fun BusArriveCard(
    busArriveModel: BusArriveModel,
    lineViewModel: LineViewModel,
) {
    val lineColorList by lineViewModel.lineColorList.collectAsState()

    var color = Color.Black

    for (i in lineColorList) {
        if (i.line_id == busArriveModel.lineId) {
            color = lineColor(i.line_kind.toString())
        }
    }

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
                    color = color
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
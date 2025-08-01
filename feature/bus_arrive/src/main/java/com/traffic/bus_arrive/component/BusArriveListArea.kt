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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.bus_arrive.model.BusArriveItemModel
import com.traffic.common.R
import com.traffic.common.lineTestColor

@Composable
fun BusArriveListArea(
    busArriveList: List<BusArriveItemModel>,
) {

    if(busArriveList.isEmpty()){
        NoBusArrive()
    } else {
        BusArriveList(
            busArriveList = busArriveList
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
private fun BusArriveList(
    busArriveList: List<BusArriveItemModel>,
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
private fun BusArriveCard(
    busArriveModel: BusArriveItemModel,
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
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                    ,
                    colors = CardDefaults.cardColors(
                        containerColor = lineTestColor(busArriveModel.lineKind ?: "1")
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 8.dp)
                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = busArriveModel.lineName!!,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.height(40.dp),
                            color = Color.White
                        )
                    }
                }

                Text(
                    text = "${busArriveModel.remainMin}분",
                    modifier = Modifier.wrapContentWidth(),
                    textAlign = TextAlign.End,
                    color = Color.Red,
                    fontWeight = FontWeight.SemiBold
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
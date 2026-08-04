package com.traffic.presentation.screens.bus_arrive.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.design.R
import com.traffic.design.component.performAnd
import com.traffic.design.ui.theme.MainColor
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.design.ui.theme.White
import com.traffic.presentation.model.BusArriveItemModel

@Composable
fun BusArriveSection(
    isLoading: Boolean,
    busArriveList: List<BusArriveItemModel>,
    onClickBusArriveCard: (String) -> Unit,
    onClickPinned: (String, Boolean) -> Unit,
) {
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        busArriveList.isEmpty() -> BusArriveEmptyContent()
        else -> BusArriveList(busArriveList = busArriveList, onClickBusArriveCard = onClickBusArriveCard, onClickPinned = onClickPinned)
    }

}

@Composable
private fun BusArriveEmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.bus_arrive_no_data),
            style = TrafficTheme.typography.empty,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            color = TrafficTheme.colors.textPrimary
        )
    }
}

@Composable
private fun BusArriveList(
    busArriveList: List<BusArriveItemModel>,
    onClickBusArriveCard: (String) -> Unit,
    onClickPinned: (String, Boolean) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(
            items = busArriveList,
            key = { index, item -> "${item.lineId}_$index" }
        ) { index, item ->
            BusArriveCard(
                haptic = haptic,
                busArriveModel = item,
                onClickBusArriveCard = onClickBusArriveCard,
                onClickPinned = onClickPinned,
            )
        }
    }
}

@Composable
private fun BusArriveCard(
    haptic: HapticFeedback,
    busArriveModel: BusArriveItemModel,
    onClickBusArriveCard: (String) -> Unit,
    onClickPinned: (String, Boolean) -> Unit,
) {
    Card(
        onClick = { busArriveModel.lineId?.let(onClickBusArriveCard) },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
        ,
        colors = CardDefaults.cardColors(containerColor = TrafficTheme.colors.cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(size = 16.dp),
        border = BorderStroke(width = 1.dp, color = TrafficTheme.colors.cardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = busArriveModel.lineColor),
                shape = RoundedCornerShape(size = 20.dp),
                modifier = Modifier.wrapContentSize()
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = busArriveModel.lineName.orEmpty(),
                        style = TrafficTheme.typography.lineName,
                        color = White
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.bus_arrive_minutes_later, busArriveModel.remainMin.orEmpty()),
                        style = TrafficTheme.typography.cardTitle,
                        color = TrafficTheme.colors.textPrimary
                    )
                    // 저상버스인 경우 태그 뱃지 표시
                    if (busArriveModel.lowBus == "1") {
                        Spacer(modifier = Modifier.width(6.dp))
                        LowFloorBadge()
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        R.string.bus_arrive_current_position,
                        busArriveModel.busStopName.orEmpty(),
                        busArriveModel.remainStop.orEmpty()
                    ),
                    style = TrafficTheme.typography.cardBody,
                    color = TrafficTheme.colors.textPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            IconButton(
                onClick = haptic.performAnd {
                    busArriveModel.lineId?.let { onClickPinned(it, busArriveModel.isPinned) }
                },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF3F4F6), shape = RoundedCornerShape(10.dp))
            ) {
                Icon(
                    imageVector = Icons.Filled.PushPin,
                    contentDescription = null,
                    tint = if (busArriveModel.isPinned) MainColor else Color.LightGray,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
private fun LowFloorBadge() {
    Surface(
        color = Color(0xFFE8F5E9),
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Accessible,
                contentDescription = stringResource(R.string.low_floor_bus),
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = stringResource(R.string.low_floor),
                fontSize = 11.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BusArriveCardPreview() {
    BusArriveCard(
        busArriveModel = BusArriveItemModel(
            arrive = "0",
            remainStop = "2",
            shortLineName = "순환01A",
            busId = "1001",
            metroFlag = "0",
            busStopName = "세하동",
            currStopId = "3609",
            lineId = "1",
            remainMin = "5",
            engBusStopName = "Sehadong",
            dirStart = "기점",
            dir = "방향",
            dirEnd = "종점",
            lowBus = "0",
            arriveFlag = "0",
            lineName = "순환01A",
            lineColor = Color(0xFF1E88E5),
            busLatitude = 35.11957758,
            busLongitude = 126.83257001,
            isPinned = true
        ),
        onClickBusArriveCard = {},
        onClickPinned = { _, _ -> },
        haptic = LocalHapticFeedback.current
    )
}
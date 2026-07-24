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
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.traffic.design.performAnd
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
            fontSize = 32.sp,
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

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(busArriveList) { index, item ->
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // 미세한 그림자 효과
        shape = RoundedCornerShape(16.dp), // 라운딩 확장
        border = BorderStroke(1.dp, TrafficTheme.colors.cardBorder) // 아주 연한 경계선
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 내부 패딩 확대
            verticalAlignment = Alignment.CenterVertically, // 세로축 가운데 정렬
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // [1] 왼쪽: 버스 노선 캡슐형 배지
            Card(
                colors = CardDefaults.cardColors(containerColor = busArriveModel.lineColor),
                shape = RoundedCornerShape(20.dp), // 타원 캡슐형태
                modifier = Modifier.wrapContentSize()
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = busArriveModel.lineName.orEmpty(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            // [2] 가운데: 실시간 도착 정보 (세로 계층 구조)
            Column(
                modifier = Modifier.weight(1f) // 가운데 영역이 남은 공간을 차지하도록 설정
            ) {
                Text(
                    text = "${busArriveModel.remainMin}분 후 도착",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TrafficTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "현재: ${busArriveModel.busStopName} (${busArriveModel.remainStop}정거장 전)",
                    fontSize = 13.sp,
                    color = TrafficTheme.colors.textPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            // [3] 오른쪽: 독립된 핀 고정 버튼
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